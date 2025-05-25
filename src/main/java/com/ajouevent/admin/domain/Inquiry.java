package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "inquiries")
@Builder
public class Inquiry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Member member; // 작성자

    private String title;
    private String content;

    @Enumerated(EnumType.STRING)
    private InquiryStatus status; // PENDING, ANSWERED, REJECTED

    private String answer; // 관리자가 달아주는 답변

    private LocalDateTime createdAt;
    private LocalDateTime answeredAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.status = InquiryStatus.PENDING;
    }

    public void answer(String answer) {
        this.answer = answer;
        this.answeredAt = LocalDateTime.now();
        this.status = InquiryStatus.ANSWERED;
    }

    public void reject(String answer) {
        this.answer = answer;
        this.answeredAt = LocalDateTime.now();
        this.status = InquiryStatus.REJECTED;
    }
}
