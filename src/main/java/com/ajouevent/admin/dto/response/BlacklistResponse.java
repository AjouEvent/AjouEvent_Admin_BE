package com.ajouevent.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class BlacklistResponse {
    private Long memberId;
    private String memberName;
    private String reason;
    private LocalDateTime createdAt;
}

