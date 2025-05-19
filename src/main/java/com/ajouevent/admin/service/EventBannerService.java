package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.EventBanner;
import com.ajouevent.admin.dto.request.EventBannerRequestDto;
import com.ajouevent.admin.repository.EventBannerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventBannerService {
    private final EventBannerRepository eventBannerRepository;

    public List<EventBanner> getActiveBanners() {
        LocalDate today = LocalDate.now();
        return eventBannerRepository.findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByBannerOrderAsc(today, today);
    }

    // Create Banner
    public EventBanner createBanner(EventBannerRequestDto dto) {
        EventBanner banner = EventBanner.builder()
                .bannerOrder(dto.getBannerOrder())
                .imgUrl(dto.getImgUrl())
                .siteUrl(dto.getSiteUrl())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .build();
        return eventBannerRepository.save(banner);
    }

    // Update Banner
    public EventBanner updateBanner(Long id, EventBannerRequestDto dto) {
        EventBanner banner = eventBannerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("배너가 존재하지 않습니다: " + id));

        banner.setBannerOrder(dto.getBannerOrder());
        banner.setImgUrl(dto.getImgUrl());
        banner.setSiteUrl(dto.getSiteUrl());
        banner.setStartDate(dto.getStartDate());
        banner.setEndDate(dto.getEndDate());

        return eventBannerRepository.save(banner);
    }

    // Delete Banner
    public void deleteBanner(Long id) {
        eventBannerRepository.deleteById(id);
    }
}

