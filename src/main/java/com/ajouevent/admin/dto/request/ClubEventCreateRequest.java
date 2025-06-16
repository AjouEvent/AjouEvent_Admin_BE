package com.ajouevent.admin.dto.request;

import com.ajouevent.admin.domain.Type;
import lombok.Getter;

@Getter
public class ClubEventCreateRequest {
    private String title;
    private String content;
    private String writer;
    private String subject;
    private String url;
    private Type type;
}