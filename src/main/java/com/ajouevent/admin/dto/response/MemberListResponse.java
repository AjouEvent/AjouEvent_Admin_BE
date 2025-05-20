package com.ajouevent.admin.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberListResponse {
    private List<MemberResponse> members;

    public static MemberListResponse from(List<MemberResponse> members) {
        return MemberListResponse.builder()
                .members(members)
                .build();
    }
}

