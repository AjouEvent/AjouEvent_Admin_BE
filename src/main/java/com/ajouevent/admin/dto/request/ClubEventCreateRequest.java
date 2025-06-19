package com.ajouevent.admin.dto.request;

import com.ajouevent.admin.domain.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ClubEventCreateRequest {
    private String title;
    private String content;
    private String writer;
    private Long subjectId;
    private String url;
    private Type type;
}