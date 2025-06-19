package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.ClubEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ClubEventListResponse {
    private List<ClubEventResponse> events;
}