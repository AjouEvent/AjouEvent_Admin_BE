package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.ChangeRoleRequest;
import com.ajouevent.admin.dto.request.PermissionUpdateRequest;
import com.ajouevent.admin.dto.response.MemberListResponse;
import com.ajouevent.admin.dto.response.PermissionUpdateResponse;
import com.ajouevent.admin.service.MemberPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

import static java.util.Collections.emptyMap;

@RestController
@RequestMapping("/api/admin/members")
@RequiredArgsConstructor
public class MemberPermissionController {

    private final MemberPermissionService memberPermissionService;

    @GetMapping
    public ResponseEntity<MemberListResponse> getMembers(@RequestParam(value = "role", required = false) String role) {
        if (role == null) {
            return ResponseEntity.ok(memberPermissionService.getAllMembers());
        }
        return ResponseEntity.ok(memberPermissionService.getMembersByRole(Enum.valueOf(com.ajouevent.admin.domain.RoleType.class, role)));
    }

    @PatchMapping("/{id}/role")
    public ResponseEntity<Map<String, Objects>> changeRole(@PathVariable Long id,
                                                           @RequestBody ChangeRoleRequest request) {
        memberPermissionService.changeRole(id, request.getRole());
        return ResponseEntity.ok(emptyMap());
    }

    @PutMapping("/{id}/permissions")
    public ResponseEntity<PermissionUpdateResponse> updatePermissions(
            @PathVariable Long id,
            @RequestBody PermissionUpdateRequest request) {
        PermissionUpdateResponse response = memberPermissionService.updateMemberPermissions(id, request);
        return ResponseEntity.ok(response);
    }
}