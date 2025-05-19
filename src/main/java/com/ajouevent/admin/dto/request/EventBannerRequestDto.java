package com.ajouevent.admin.dto.request;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventBannerRequestDto {
    private Long bannerOrder;
    private String imgUrl;
    private String siteUrl;
    private LocalDate startDate;
    private LocalDate endDate;
}
