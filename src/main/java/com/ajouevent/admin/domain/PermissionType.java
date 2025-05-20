package com.ajouevent.admin.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PermissionType {
    CAN_VIEW_EVENT("이벤트 보기"),
    CAN_EDIT_EVENT("이벤트 수정"),
    CAN_SEND_NOTIFICATION("알림 발송");

    private final String description;
}

