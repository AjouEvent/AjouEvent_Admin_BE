package com.ajouevent.admin.dto.request;

import com.ajouevent.admin.domain.PermissionType;
import lombok.Getter;

@Getter
public class PermissionUpdateRequest {
    private PermissionType permission;
}
