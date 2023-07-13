package org.upriser.hitcsecs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.upriser.hitcsecs.entity.Admin;
import org.upriser.hitcsecs.error.CustomException;
import org.upriser.hitcsecs.repository.AdminRepository;

@Service
public class AdminServiceImpl implements AdminService{

    private final AdminRepository adminRepository;
    @Autowired
    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public void verifyAdmin(String userId, String password) throws CustomException {
        Admin admin = adminRepository.findById(userId)
                .orElseThrow(() -> new CustomException(HttpStatus.NOT_FOUND,
                        "Admin not exist with user id: "+userId
                ));
        if(!admin.getPassword().equals(password)){
            throw new CustomException(HttpStatus.BAD_REQUEST,
                    "Incorrect password for Admin with user id: "+userId);
        }
    }
}
