package org.upriser.hitcsecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.upriser.hitcsecs.entity.MarActivity;
import org.upriser.hitcsecs.service.MarActivityService;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class MarActivityController {

    private final MarActivityService marActivityService;
    @Autowired
    public MarActivityController(MarActivityService marActivityService) {
        this.marActivityService = marActivityService;
    }

    @PostMapping("/add-mar-activity")
    public ResponseEntity<?> addMarActivity(@RequestBody MarActivity marActivity){
        marActivity = marActivityService.addActivity(marActivity);

        return ResponseEntity.status(HttpStatus.OK)
                .body(marActivity);
    }
    @GetMapping("/fetch-all-mar-activities")
    public ResponseEntity<?> fetchAllMarActivities(){
        List<MarActivity> marActivityList = marActivityService.fetchAllMarActivities();

        return ResponseEntity.status(HttpStatus.OK)
                .body(marActivityList);
    }
}
