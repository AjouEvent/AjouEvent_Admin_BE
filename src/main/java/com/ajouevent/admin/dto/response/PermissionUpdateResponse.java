package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.PermissionType;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class PermissionUpdateResponse {
    private Long memberId;
    private Set<PermissionType> added;
    private Set<PermissionType> removed;
}
