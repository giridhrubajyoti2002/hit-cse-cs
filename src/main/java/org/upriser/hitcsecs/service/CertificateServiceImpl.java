package org.upriser.hitcsecs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.upriser.hitcsecs.entity.Certificate;
import org.upriser.hitcsecs.entity.MarActivity;
import org.upriser.hitcsecs.entity.MoocsCourse;
import org.upriser.hitcsecs.entity.Student;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.repository.CertificateRepository;
import org.upriser.hitcsecs.repository.MarActivityRepository;
import org.upriser.hitcsecs.repository.MoocsCourseRepository;
import org.upriser.hitcsecs.repository.StudentRepository;
import org.upriser.hitcsecs.utils.Utils;

import java.io.IOException;
import java.util.List;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final StudentRepository studentRepository;
    private final MarActivityRepository marActivityRepository;
    private final MoocsCourseRepository moocsCourseRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, StudentRepository studentRepository, MarActivityRepository marActivityRepository, MoocsCourseRepository moocsCourseRepository) {
        this.certificateRepository = certificateRepository;
        this.studentRepository = studentRepository;
        this.marActivityRepository = marActivityRepository;
        this.moocsCourseRepository = moocsCourseRepository;
    }

    @Override
    public Certificate addCertificate(String studentId, CertificateType certificateType,
                                      String activityOrCourseId, MultipartFile file
    ) throws IOException, CustomException {
        Student student = studentRepository.findById(studentId).orElseThrow(() ->
                new CustomException(HttpStatus.NOT_FOUND,
                        "Student not found with id: "+studentId));
        Certificate certificate = Certificate.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .certificateType(certificateType)
                .student(student)
                .yearType(student.getCurrentYear())
                .data(Utils.compressImage(file.getBytes()))
                .build();
        List<Certificate> certificateList = certificateRepository.
                findByStudentStudentIdAndCertificateTypeAndActivityOrCourseId(
                        studentId, certificateType, activityOrCourseId);
        if(certificateType==CertificateType.MAR){
            MarActivity marActivity = marActivityRepository.findById(activityOrCourseId)
                            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                                    "MAR activity not found with id: "+activityOrCourseId
                            ));
            if(certificateList.size()*marActivity.getActivityPoints() >= marActivity.getActivityMaxPoints()) {
                throw new CustomException(HttpStatus.BAD_REQUEST,
                        "Maximum points of this MAR activity reached." +
                                " Please submit certificate for another activity types.");
            }certificate.setActivityOrCourseId(marActivity.getActivityId());
        }else if(certificateType==CertificateType.MOOCS){
            MoocsCourse moocsCourse = moocsCourseRepository.findById(activityOrCourseId)
                            .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                                    "MOOCS course not found with id: "+activityOrCourseId));
            if(!certificateList.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST,
                        "You have already submitted this course certificate." +
                                " Please submit certificate for another courses.");
            }certificate.setActivityOrCourseId(moocsCourse.getCourseId());
        }

        student.getCertificates().add(certificate);
        studentRepository.save(student);
        certificate = student.getCertificates().get(student.getCertificates().size()-1);

        String downloadUrl = ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path("/download-certificate/")
                .path(certificate.getCertificateId())
                .toUriString();
        certificate.setDownloadUrl(downloadUrl);
        return certificateRepository.save(certificate);
    }

    @Override
    public Certificate fetchCertificate(String certificateId) throws CustomException {
        Certificate certificate = certificateRepository
                .findById(certificateId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,"Certificate not found with id: " + certificateId));
        certificate.setData(Utils.decompressImage(certificate.getData()));
        return certificate;
    }

    @Override
    public void deleteCertificate(String certificateId) throws CustomException {
        certificateRepository.findById(certificateId).orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, "Certificate not found with id: "+certificateId));
        certificateRepository.deleteById(certificateId);
    }

    @Override
    public void verifyCertificate(String certificateId) throws CustomException {
        Certificate certificate = certificateRepository.findById(certificateId).orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, "Certificate not found with id: "+certificateId));
        certificate.setVerified(true);
        certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> fetchCertificatesByYear(String studentId, YearType yearType) throws CustomException {
        studentRepository.findById(studentId).orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, "Student not found with id: "+studentId
        ));
        return certificateRepository.findByStudentStudentIdAndYearType(studentId, yearType);
    }

    @Override
    public List<Certificate> fetchCertificatesByMarMoocs(String studentId, CertificateType certificateType) throws CustomException {
        studentRepository.findById(studentId).orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, "Student not found with id: "+studentId
        ));
        return certificateRepository.findByStudentStudentIdAndCertificateType(studentId, certificateType);
    }

    @Override
    public List<Certificate> fetchCertificatesByYearAndMarMoocs(String studentId, YearType yearType, CertificateType certificateType) throws CustomException {
        studentRepository.findById(studentId).orElseThrow(() -> new CustomException(
                HttpStatus.NOT_FOUND, "Student not found with id: "+studentId
        ));
        return certificateRepository.findByStudentStudentIdAndYearTypeAndCertificateType(
                studentId, yearType,certificateType);
    }



}
