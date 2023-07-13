package org.upriser.hitcsecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upriser.hitcsecs.entity.Student;
import org.upriser.hitcsecs.enums.CertificateType;
import org.upriser.hitcsecs.enums.YearType;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.service.StudentService;

import java.util.List;

@RestController
public class StudentController {

    private final StudentService studentService;
    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping(value = "fetch-all-students")
    public ResponseEntity<?> fetchAllStudents(){
        List<Student> students = studentService.fetchAllStudents();

        return ResponseEntity.status(HttpStatus.OK)
                .body(students);
    }
    @PostMapping("add-student")
    public ResponseEntity<?> addStudent(@RequestBody Student student){
        student = studentService.addStudent(student);

        return ResponseEntity.status(HttpStatus.OK)
                .body(student);
    }
    @GetMapping("fetch-student")
    public ResponseEntity<?> fetchStudent(@RequestParam("studentId") String studentId)
            throws CustomException {
        Student student = studentService.fetchStudent(studentId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(student);
    }
    @GetMapping("fetch-all-students-by-year")
    public ResponseEntity<?> fetchAllStudentsByYear(@RequestParam("yearType") YearType yearType){
        List<Student> studentList = studentService.fetchAllStudentsByYear(yearType);
        return ResponseEntity.status(HttpStatus.OK)
                .body(studentList);
    }
    @PostMapping("remove-all-students-by-year")
    public ResponseEntity<?> removeAllStudentsByYear(@RequestParam("yearType") YearType yearType){
        studentService.removeAllStudentsByYear(yearType);
        return ResponseEntity.status(HttpStatus.OK)
                .body("All the students of "+yearType+" year removed successfully.");
    }
    @DeleteMapping("remove-student")
    public ResponseEntity<?> removeStudent(@RequestParam("studentId") String studentId) {
        studentService.removeStudent(studentId);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Student removed successfully with id: "+studentId);
    }

    @PostMapping("promote-students")
    public ResponseEntity<?> promoteStudents(@RequestParam("yearType") YearType currentYear)
            throws CustomException {

        studentService.promoteStudents(currentYear);

        return ResponseEntity.status(HttpStatus.OK)
                .body("Success");
    }

    @GetMapping("get-total-points")
    public ResponseEntity<?> getTotalPoints(
            @RequestParam("studentId") String studentId,
            @RequestParam("yearType") YearType yearType,
            @RequestParam("certificateType") CertificateType certificateType
            ){

        Integer totalPoints = studentService.getTotalPoints(studentId, yearType, certificateType);
        return ResponseEntity.status(HttpStatus.OK)
                .body(yearType+" Year "+certificateType+" points: "+totalPoints);
    }
}
