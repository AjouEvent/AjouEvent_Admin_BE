package com.ajouevent.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EventBannerResponse {
    private Long eventBannerId;    // 추가
    private Long bannerOrder;
    private String imgUrl;
    private String siteUrl;
    private LocalDate startDate;   // 추가
    private LocalDate endDate;     // 추가
    private boolean isPosted;      // 추가
}