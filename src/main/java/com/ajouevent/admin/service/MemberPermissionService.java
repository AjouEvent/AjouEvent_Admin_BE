package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.domain.RoleType;
import com.ajouevent.admin.dto.response.MemberListResponse;

public interface MemberPermissionService {
    MemberListResponse getAllMembers();
    MemberListResponse getMembersByRole(RoleType role);
    void changeRole(Long memberId, RoleType newRole);
    void grantPermission(Long memberId, PermissionType permissionType);
    void revokePermission(Long memberId, PermissionType permissionType);
}