package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.domain.MemberPermission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.domain.RoleType;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
public class MemberResponse {
    private Long id;
    private String email;
    private String name;
    private RoleType role;
    private Set<PermissionType> permissions; // 최종 권한 목록

    public static MemberResponse from(Member member, Set<PermissionType> effectivePermissions) {
        return MemberResponse.builder()
                .id(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getRole())
                .permissions(effectivePermissions)
                .build();
    }
}
