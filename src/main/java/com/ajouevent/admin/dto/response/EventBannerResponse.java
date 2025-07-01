package com.ajouevent.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class EventBannerResponse {
    private Long eventBannerId;
    private Long bannerOrder;
    private String imgUrl;
    private String siteUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isPosted;

}