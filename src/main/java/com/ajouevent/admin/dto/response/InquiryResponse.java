package com.ajouevent.admin.dto.response;

import com.ajouevent.admin.domain.Inquiry;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class InquiryResponse {
    private Long id;
    private String memberName;
    private String title;
    private String content;
    private String status; // PENDING, ANSWERED, REJECTED
    private String answer;
    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    public static InquiryResponse from(Inquiry inquiry, String memberName) {
        return InquiryResponse.builder()
                .id(inquiry.getId())
                .memberName(memberName)
                .title(inquiry.getTitle())
                .content(inquiry.getContent())
                .status(inquiry.getStatus().name())
                .answer(inquiry.getAnswer())
                .createdAt(inquiry.getCreatedAt())
                .answeredAt(inquiry.getAnsweredAt())
                .build();
    }
}
