package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.SignUpRequest;
import com.ajouevent.admin.dto.request.LoginRequest;
import com.ajouevent.admin.dto.response.AdminAuthResponse;
import com.ajouevent.admin.service.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
                                                   HttpServletRequest req,
                                                   HttpServletResponse res) {
        AdminAuthResponse response = adminUserService.login(request);

        HttpSession session = req.getSession(true);
        session.setAttribute("adminId", response.getId());
        res.setHeader("X-Session-Id", session.getId());

        return ResponseEntity.ok(response);
    }

    //세션 인증을 먼저 하고 삭제 하는 로직.
    @PostMapping("/admin/logout")
    public ResponseEntity<Map<String, Object>> logout(HttpServletRequest req) {
        HttpSession session = req.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return ResponseEntity.ok(emptyMap());
    }
}
