package com.ajouevent.admin.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminTestController {

    @GetMapping("/")
    public ResponseEntity<?> checkSession(HttpSession session) {
        Long adminId = (Long) session.getAttribute("adminId");

        return ResponseEntity.ok()
                .body("현재 로그인한 관리자 ID: " + adminId);
    }
}
