package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.ClubEventSubject;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class EventSubjectListResponse {
    private final List<Map<String, Object>> subjects;

    public static EventSubjectListResponse from(List<ClubEventSubject> entities) {
        List<Map<String, Object>> items = entities.stream()
                .map(subject -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", subject.getId());
                    map.put("name", subject.getName());
                    return map;
                })
                .toList();
        return new EventSubjectListResponse(items);
    }
}
