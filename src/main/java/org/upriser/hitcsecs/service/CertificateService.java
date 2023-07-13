package org.upriser.hitcsecs.service;

import org.springframework.web.multipart.MultipartFile;
import org.upriser.hitcsecs.entity.Certificate;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;

import java.io.IOException;
import java.util.List;

public interface CertificateService {

    Certificate fetchCertificate(String studentId) throws CustomException;

    Certificate addCertificate(
            String studentId, CertificateType certificateType,
            String activityOrCourseId, MultipartFile file
    ) throws IOException, CustomException;

    void deleteCertificate(String certificateId) throws CustomException;

    void verifyCertificate(String certificateId) throws CustomException;

    List<Certificate> fetchCertificatesByYear(String studentId, YearType yearType) throws CustomException;

    List<Certificate> fetchCertificatesByMarMoocs(String studentId, CertificateType certificateType) throws CustomException;

    List<Certificate> fetchCertificatesByYearAndMarMoocs(String studentId, YearType yearType, CertificateType certificateType) throws CustomException;
}
