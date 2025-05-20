package com.ajouevent.admin.dto.request;

import com.ajouevent.admin.domain.RoleType;
import lombok.Getter;

@Getter
public class ChangeRoleRequest {
    private RoleType role;
}
