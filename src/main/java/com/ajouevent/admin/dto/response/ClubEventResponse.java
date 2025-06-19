package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.ClubEvent;
import com.ajouevent.admin.domain.ClubEventSubject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class ClubEventResponse {
    private Long eventId;
    private String title;
    private String content;
    private String writer;
    private String subject;
    private String url;
    private Long likesCount;
    private Long viewCount;
//    private String type;
    private boolean isHidden;
    private LocalDateTime createdAt;
    private List<String> imageUrls;

    public static ClubEventResponse from(ClubEvent entity, List<String> imageUrls, ClubEventSubject subject) {
        return ClubEventResponse.builder()
                .eventId(entity.getEventId())
                .title(entity.getTitle())
                .content(entity.getContent())
                .writer(entity.getWriter())
                .subject(subject.getName())
                .url(entity.getUrl())
                .likesCount(entity.getLikesCount())
                .viewCount(entity.getViewCount())
//                .type(entity.getType().name())
                .isHidden(entity.isHidden())
                .createdAt(entity.getCreatedAt())
                .imageUrls(imageUrls)
                .build();
    }
}