package com.ajouevent.admin.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String name;

    @Column
    private String password;

    @Column
    private String major;

    @Column
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType role;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("permissionType ASC")
    @ToString.Exclude
    @JsonIgnore
    private List<MemberPermission> overriddenPermissions = new ArrayList<>();


    @Builder
    public Member(String name, String email, RoleType role) {
        this.name = name;
        this.email = email;
        this.role = role;
        this.overriddenPermissions = new ArrayList<>();
        this.setPermissionsByRole(role);
    }

    public void updatePermissions(Set<PermissionType> newPermissions) {
        // 현재 권한 추출
        Set<PermissionType> current = this.overriddenPermissions.stream()
                .map(MemberPermission::getPermissionType)
                .collect(Collectors.toSet());

        // 추가할 권한
        Set<PermissionType> toAdd = new HashSet<>(newPermissions);
        toAdd.removeAll(current);

        // 제거할 권한
        Set<PermissionType> toRemove = new HashSet<>(current);
        toRemove.removeAll(newPermissions);

        // 제거
        this.overriddenPermissions.removeIf(p -> toRemove.contains(p.getPermissionType()));

        // 추가
        toAdd.forEach(this::addPermission);
    }

    public void addPermission(PermissionType type) {
        MemberPermission permission = new MemberPermission();
        permission.setPermissionType(type);
        permission.setMember(this);
        this.overriddenPermissions.add(permission);
    }

    public void setRole(RoleType role) {
        this.role = role;
    }

    public void setPermissionsByRole(RoleType role) {
        for (PermissionType permissionType : role.getDefaultPermissions()) {
            this.addPermission(permissionType);
        }
    }

    public void removePermission(PermissionType type) {
        overriddenPermissions.removeIf(p -> p.getPermissionType() == type);
    }

    public boolean hasPermission(PermissionType type) {
        return overriddenPermissions.stream()
                .anyMatch(p -> p.getPermissionType() == type);
    }

}
