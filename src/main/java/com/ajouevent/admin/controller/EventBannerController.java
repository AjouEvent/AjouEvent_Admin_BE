package com.ajouevent.admin.controller;

import com.ajouevent.admin.domain.EventBanner;
import com.ajouevent.admin.dto.response.EventBannerResponse;
import com.ajouevent.admin.service.EventBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/banners")
@RequiredArgsConstructor
public class EventBannerController {

    private final EventBannerService eventBannerService;

    @GetMapping("/active")
    public ResponseEntity<List<EventBannerResponse>> getActiveBanners() {
        List<EventBanner> activeBanners = eventBannerService.getActiveBanners();

        List<EventBannerResponse> response = activeBanners.stream()
                .map(b -> new EventBannerResponse(b.getBannerOrder(), b.getImgUrl(), b.getSiteUrl()))
                .toList();

        return ResponseEntity.ok(response);
    }
}
