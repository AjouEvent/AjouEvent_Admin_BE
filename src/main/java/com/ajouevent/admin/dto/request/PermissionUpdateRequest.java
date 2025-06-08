package com.ajouevent.admin.dto.request;

import com.ajouevent.admin.domain.PermissionType;
import lombok.Data;
import java.util.Set;


@Data
public class PermissionUpdateRequest {
    private Set<PermissionType> permissions;
}