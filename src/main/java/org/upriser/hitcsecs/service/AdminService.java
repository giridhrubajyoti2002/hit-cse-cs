package org.upriser.hitcsecs.service;

import org.upriser.hitcsecs.error.CustomException;

public interface AdminService {
    void verifyAdmin(String userId, String password) throws CustomException;
}
