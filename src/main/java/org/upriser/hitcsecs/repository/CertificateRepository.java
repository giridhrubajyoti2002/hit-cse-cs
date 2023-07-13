package org.upriser.hitcsecs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.upriser.hitcsecs.entity.Certificate;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;

import java.util.List;

@Repository
public interface CertificateRepository extends JpaRepository<Certificate,String> {

//    @Query("select c from Certificate c where c.student.studentId=?1 and c.certificateYear=?2")
    List<Certificate> findByStudentStudentIdAndYearType(
            String studentId, YearType yearType);

    List<Certificate> findByStudentStudentIdAndCertificateType(
            String studentId, CertificateType certificateType);

    @Query("select c from Certificate c where c.student.studentId=?1 and " +
            "c.yearType=?2 and c.certificateType=?3")
    List<Certificate> findByStudentStudentIdAndYearTypeAndCertificateType(
            String studentId, YearType yearType, CertificateType certificateType);


    @Query("select c from Certificate c where c.student.studentId=?1 and " +
            "c.certificateType=?2 and c.activityOrCourseId=?3")
    List<Certificate> findByStudentStudentIdAndCertificateTypeAndActivityOrCourseId(
            String studentId, CertificateType certificateType, String activityOrCourseId);


}
