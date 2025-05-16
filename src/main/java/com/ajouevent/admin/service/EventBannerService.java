package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.EventBanner;
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
}
