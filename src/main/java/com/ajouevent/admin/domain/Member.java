package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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
    private List<MemberPermission> overriddenPermissions = new ArrayList<>();


    @Builder
    public Member(String name, String email, RoleType role) {
        this.name = name;
        this.email = email;
        this.role = role;

        this.overriddenPermissions = new ArrayList<>();
        for (PermissionType defaultPermission : role.getDefaultPermissions()) {
            addPermission(defaultPermission);
        }
    }
    public void addPermission(PermissionType type) {
        MemberPermission permission = new MemberPermission();
        permission.setMember(this);
        permission.setPermissionType(type);
        overriddenPermissions.add(permission);
    }

    public void removePermission(PermissionType type) {
        overriddenPermissions.removeIf(p -> p.getPermissionType() == type);
    }

    public boolean hasPermission(PermissionType type) {
        return overriddenPermissions.stream()
                .anyMatch(p -> p.getPermissionType() == type);
    }
}