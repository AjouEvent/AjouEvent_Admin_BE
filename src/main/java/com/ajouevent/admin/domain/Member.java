package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
    public Member(Long id, String email, String name, String password, String major, String phone, RoleType role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.major = major;
        this.phone = phone;
        this.role = role;
    }
}