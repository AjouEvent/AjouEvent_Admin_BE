package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @JoinColumn(name = "member_id", nullable = false, unique = true)
    @OnDelete(action = OnDeleteAction.CASCADE) // 멤버가 삭제되면 블랙리스트 행도 삭제 된다
    private Member member;

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
}
