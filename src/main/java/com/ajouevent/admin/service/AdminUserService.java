package com.ajouevent.admin.service;

import com.ajouevent.admin.dto.request.SignUpRequest;
import com.ajouevent.admin.dto.request.LoginRequest;
import com.ajouevent.admin.dto.response.AdminAuthResponse;
import jakarta.servlet.http.HttpSession;

public interface AdminUserService {
    AdminAuthResponse signUp(SignUpRequest request);
    AdminAuthResponse login(LoginRequest request);
    void logout(HttpSession session);
}
