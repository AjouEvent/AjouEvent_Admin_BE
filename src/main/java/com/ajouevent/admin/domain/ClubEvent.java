package com.ajouevent.admin.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.BatchSize;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "club_events")
public class ClubEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;

    @Column
    private String title;

    @Column(length = 50000)
    private String content;

    @Column // 게시글 작성자(작성 기관)
    private String writer;

    @Column // 게시글 생성 시간
    private LocalDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "club_event_subject_id", nullable = false)
    private ClubEventSubject subject;

    @Column // 원래 공지사항 url
    private String url;

    @Column // 찜한 수 (default는 0)
    private Long likesCount;

    @Column // 조회 수 (default는 0)
    private Long viewCount;

    @Column(length = 50000)
    @Enumerated(value = EnumType.STRING)
    private Type type;

    @BatchSize(size=100) //
    @OneToMany(mappedBy = "clubEvent", fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.REMOVE}, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<ClubEventImage> clubEventImageList = new ArrayList<>();

    @Column(nullable = false)
    private boolean isHidden;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        if (this.likesCount == null) this.likesCount = 0L;
        if (this.viewCount == null) this.viewCount = 0L;
    }

    public void incrementLikes() {
        this.likesCount++;
    }

    public void decreaseLikes() {
        this.likesCount--;
    }

    public void hide() {
        this.isHidden = true;
    }

    public void unhide() {
        this.isHidden = false;
    }
}