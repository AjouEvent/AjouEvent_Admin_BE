package com.ajouevent.admin.controller;

import com.ajouevent.admin.domain.EventBanner;
import com.ajouevent.admin.dto.request.EventBannerRequestDto;
import com.ajouevent.admin.dto.response.EventBannerResponse;
import com.ajouevent.admin.service.EventBannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/banners")
@RequiredArgsConstructor
public class EventBannerController {

    private final EventBannerService eventBannerService;

//    @GetMapping("/active")
//    public ResponseEntity<List<EventBannerResponse>> getActiveBanners() {
//        List<EventBanner> activeBanners = eventBannerService.getActiveBanners();
//
//        List<EventBannerResponse> response = activeBanners.stream()
//                .map(b -> new EventBannerResponse(b.getBannerOrder(), b.getImgUrl(), b.getSiteUrl()))
//                .toList();
//
//        return ResponseEntity.ok(response);
//    }

    @GetMapping("/active")
    public ResponseEntity<List<EventBannerResponse>> getActiveBanners() {
        List<EventBanner> activeBanners = eventBannerService.getActiveBanners();

        List<EventBannerResponse> response = activeBanners.stream()
                .map(b -> new EventBannerResponse(
                        b.getEventBannerId(),    // 추가
                        b.getBannerOrder(),
                        b.getImgUrl(),
                        b.getSiteUrl(),
                        b.getStartDate(),        // 추가
                        b.getEndDate(),          // 추가
                        b.isPosted()             // 추가
                ))
                .toList();

        return ResponseEntity.ok(response);
    }

    // Create Banner
    @PostMapping
    public ResponseEntity<EventBanner> createBanner(@RequestBody EventBannerRequestDto dto) {
        EventBanner saved = eventBannerService.createBanner(dto);
        return ResponseEntity.ok(saved);
    }

    // Update Banner
    @PutMapping("/{id}")
    public ResponseEntity<EventBanner> updateBanner(@PathVariable Long id, @RequestBody EventBannerRequestDto dto) {
        EventBanner updated = eventBannerService.updateBanner(id, dto);
        return ResponseEntity.ok(updated);
    }

    // Delete Banner
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBanner(@PathVariable Long id) {
        eventBannerService.deleteBanner(id);
        return ResponseEntity.noContent().build();
    }
}
