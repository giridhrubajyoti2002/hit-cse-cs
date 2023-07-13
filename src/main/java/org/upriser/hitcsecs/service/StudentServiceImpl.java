package org.upriser.hitcsecs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.upriser.hitcsecs.entity.Certificate;
import org.upriser.hitcsecs.entity.Student;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.repository.CertificateRepository;
import org.upriser.hitcsecs.repository.MarActivityRepository;
import org.upriser.hitcsecs.repository.MoocsCourseRepository;
import org.upriser.hitcsecs.repository.StudentRepository;
import org.upriser.hitcsecs.utils.Utils;

import java.util.ArrayList;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final CertificateRepository certificateRepository;
    private final MarActivityRepository marActivityRepository;
    private final MoocsCourseRepository moocsCourseRepository;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, CertificateRepository certificateRepository, MarActivityRepository marActivityRepository, MoocsCourseRepository moocsCourseRepository) {
        this.studentRepository = studentRepository;
        this.certificateRepository = certificateRepository;
        this.marActivityRepository = marActivityRepository;
        this.moocsCourseRepository = moocsCourseRepository;
    }

    @Override
    public Student addStudent(Student student) {
        student.setCertificates(new ArrayList<>());
        return studentRepository.save(student);
    }

    @Override
    public Student fetchStudent(String studentId) throws CustomException {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Student not found with id: "+studentId));
        List<Certificate> certificates = student.getCertificates();
        for (Certificate certificate:certificates){
            certificate.setData(Utils.decompressImage(certificate.getData()));
        }
        return student;
    }

    @Override
    public void removeStudent(String studentId) {
        studentRepository.deleteById(studentId);
    }

    @Override
    public List<Student> fetchAllStudents() {
        List<Student> studentList = studentRepository.findAll();
        for(Student student:studentList){
            List<Certificate> certificates = student.getCertificates();
            for (Certificate certificate:certificates){
                certificate.setData(Utils.decompressImage(certificate.getData()));
            }
        }
        return studentList;
    }

    @Override
    public void promoteStudents(YearType currentYear) throws CustomException {
        if(currentYear==YearType.FOURTH){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Fourth year students cannot be promoted further. " +
                            "You have to remove them from database.");
        } else if (currentYear==YearType.THIRD) {
            List<Student> studentList = studentRepository.findByCurrentYear(YearType.FOURTH);
            if(!studentList.isEmpty()){
                throw new CustomException(HttpStatus.BAD_REQUEST,
                        "Please promote/remove Fourth Year students from database " +
                        "before promoting Third Year students");
            }
        }
        List<Student> studentList = studentRepository.findByCurrentYear(currentYear);
        for(Student student:studentList){
            student.setCurrentYear(YearType.nextYear(currentYear));
        }
        studentRepository.saveAll(studentList);
    }

    @Override
    public List<Student> fetchAllStudentsByYear(YearType yearType) {
        return studentRepository.findByCurrentYear(yearType);
    }

    @Override
    public void removeAllStudentsByYear(YearType yearType) {
        studentRepository.deleteAllByCurrentYear(yearType);
    }

    @Override
    public Integer getTotalPoints(String studentId, YearType yearType, CertificateType certificateType) {
        List<Certificate> certificateList = certificateRepository
                .findByStudentStudentIdAndYearTypeAndCertificateType(
                studentId, yearType, certificateType);
        Integer totalPoints = 0;
        for(Certificate certificate:certificateList) {
            System.out.println(certificate.getCertificateId());
            if (!certificate.isVerified()) {
                totalPoints += certificate.getCertificateType() == CertificateType.MAR ?
                        marActivityRepository.findById(certificate.getActivityOrCourseId()).get().getActivityPoints() :
                        moocsCourseRepository.findById(certificate.getActivityOrCourseId()).get().getCourseCredit();
            }
        }
        return totalPoints;
    }

}
