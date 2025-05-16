package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.SignUpRequest;
import com.ajouevent.admin.dto.request.LoginRequest;
import com.ajouevent.admin.dto.response.AdminAuthResponse;
import com.ajouevent.admin.service.AdminUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;

    @PostMapping("/signup")
    public ResponseEntity<AdminAuthResponse> signUp(@RequestBody SignUpRequest request) {
        AdminAuthResponse response = adminUserService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody LoginRequest request) {
        AdminAuthResponse response = adminUserService.login(request);
        return ResponseEntity.ok(response);
    }
}
