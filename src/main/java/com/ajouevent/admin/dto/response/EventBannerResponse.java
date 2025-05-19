package com.ajouevent.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EventBannerResponse {
    private Long bannerOrder;
    private String imgUrl;
    private String siteUrl;
}