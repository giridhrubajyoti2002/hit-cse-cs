package org.upriser.hitcsecs.service;

import org.upriser.hitcsecs.entity.Student;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;

import java.util.List;

public interface StudentService {
    Student addStudent(Student student);

    Student fetchStudent(String studentId) throws CustomException;

    void removeStudent(String studentId);

    List<Student> fetchAllStudents();

    void promoteStudents(YearType currentYear) throws CustomException;

    List<Student> fetchAllStudentsByYear(YearType yearType);

    void removeAllStudentsByYear(YearType yearType);

    Integer getTotalPoints(String studentId, YearType yearType, CertificateType certificateType);
}
