package com.ajouevent.admin.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InquiryAnswerRequest {
    private String answer;

    @Builder
    public InquiryAnswerRequest(String answer) {
        this.answer = answer;
    }
}

