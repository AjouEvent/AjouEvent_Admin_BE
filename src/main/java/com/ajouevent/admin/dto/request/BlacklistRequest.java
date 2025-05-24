package com.ajouevent.admin.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BlacklistRequest {

    private String reason;

    @Builder
    public BlacklistRequest(String reason) {
        this.reason = reason;
    }
}

