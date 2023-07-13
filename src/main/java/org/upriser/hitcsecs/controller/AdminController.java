package org.upriser.hitcsecs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/")
    ResponseEntity<?> helloAdmin() {
        return ResponseEntity.ok().body("Hello Admin");
    }
    @PostMapping("/verify-admin")
    ResponseEntity<?> verifyAdmin(@RequestParam String userId, @RequestParam String password) throws CustomException {
        adminService.verifyAdmin(userId, password);
        return ResponseEntity.ok().body("Success");
    }
}
