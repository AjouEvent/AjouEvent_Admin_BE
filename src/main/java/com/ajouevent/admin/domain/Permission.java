package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import jdk.jfr.Registered;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Permission {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private PermissionType type;

    private String description;
}


