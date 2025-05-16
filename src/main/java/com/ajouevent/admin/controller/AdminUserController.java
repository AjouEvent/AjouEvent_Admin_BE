package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.SignUpRequest;
import com.ajouevent.admin.dto.request.LoginRequest;
import com.ajouevent.admin.dto.response.AdminAuthResponse;
import com.ajouevent.admin.service.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Collections;
import java.util.Map;
import static java.util.Collections.emptyMap;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AdminUserController {

    private final AdminUserService adminUserService;


    @PostMapping("/auth/signup")
    public ResponseEntity<AdminAuthResponse> signUp(@RequestBody SignUpRequest request) {
        AdminAuthResponse response = adminUserService.signUp(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<AdminAuthResponse> login(@RequestBody LoginRequest request,
                                                   HttpServletRequest req) {
        AdminAuthResponse response = adminUserService.login(request);

        //일단 인메모리 세션으로 관리 나중에 redis 붙일라고 함
        HttpSession session = req.getSession(true);
        session.setAttribute("adminId", response.getId());

        return ResponseEntity.ok(response);
    }

    //세션 인증을 먼저 하고 삭제 하는 로직.
    @PostMapping("/admin/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpSession session) {
        adminUserService.logout(session);
        return ResponseEntity.ok(emptyMap());
    }
}
