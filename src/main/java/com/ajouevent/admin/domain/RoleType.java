package com.ajouevent.admin.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;


@Getter
@RequiredArgsConstructor
public enum RoleType {
    USER(Set.of(PermissionType.CAN_VIEW_EVENT)),
    LEADER(Set.of(
            PermissionType.CAN_VIEW_EVENT,
            PermissionType.CAN_EDIT_EVENT,
            PermissionType.CAN_SEND_NOTIFICATION
    ));

    private final Set<PermissionType> defaultPermissions;
}

