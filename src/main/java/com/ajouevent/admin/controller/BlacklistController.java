package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.BlacklistRequest;
import com.ajouevent.admin.dto.response.BlacklistListResponse;
import com.ajouevent.admin.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Collections.emptyMap;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class BlacklistController {

    private final BlacklistService blacklistService;

    @PostMapping("/{id}/blacklist")
    public ResponseEntity<Map<String, Object>> registerBlacklist(@PathVariable Long id,
                                                                 @RequestBody BlacklistRequest request) {
        blacklistService.register(id, request.getReason());
        return ResponseEntity.ok(emptyMap());
    }

    @DeleteMapping("/{id}/blacklist")
    public ResponseEntity<Map<String, Object>> revokeBlacklist(@PathVariable Long id) {
        blacklistService.revoke(id);
        return ResponseEntity.ok(emptyMap());
    }


    @GetMapping("/blacklist")
    public ResponseEntity<BlacklistListResponse> getAllBlacklist() {
        return ResponseEntity.ok(blacklistService.getAll());
    }
}
