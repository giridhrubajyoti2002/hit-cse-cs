package org.upriser.hitcsecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upriser.hitcsecs.entity.MoocsCourse;
import org.upriser.hitcsecs.service.MoocsCourseService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class MoocsCourseController {

    private final MoocsCourseService moocsCourseService;
    @Autowired
    public MoocsCourseController(MoocsCourseService moocsCourseService) {
        this.moocsCourseService = moocsCourseService;
    }

    @PostMapping("/add-moocs-course")
    public ResponseEntity<?> addMoocsCourse(@RequestBody MoocsCourse moocsCourse){
        moocsCourse = moocsCourseService.addCourse(moocsCourse);

        return ResponseEntity.status(HttpStatus.OK)
                .body(moocsCourse);
    }
    @GetMapping("/fetch-all-moocs-courses")
    public ResponseEntity<?> fetchAllMarActivities(){
        List<MoocsCourse> moocsCourseList = moocsCourseService.fetchAllMoocsCourses();

        return ResponseEntity.status(HttpStatus.OK)
                .body(moocsCourseList);
    }
}
