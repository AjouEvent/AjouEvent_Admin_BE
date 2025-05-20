package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.EventBanner;
import com.ajouevent.admin.repository.EventBannerRepository;
import com.ajouevent.admin.service.s3.S3Uploader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor

public class BannerAutoResister {
    private final EventBannerRepository eventBannerRepository;
    private final S3Uploader s3Uploader;

    @Scheduled(cron = "0 0 0 * * *")
    public void autoPostBanner() {
        LocalDate today = LocalDate.now();

        List<EventBanner> allBanners = eventBannerRepository.findAll();

        for (EventBanner banner : allBanners) {
            boolean withinActivePeriod =
                    (banner.getStartDate() == null || !today.isBefore(banner.getStartDate())) &&
                            (banner.getEndDate() == null || !today.isAfter(banner.getEndDate()));

            if (withinActivePeriod && !banner.isPosted()) {
                log.info("게시 시작: {}", banner.getImgUrl());

                // S3 업로드가 필요한 경우 처리 (예시)
                if (!isPublicImage(banner.getImgUrl())) {
                    String uploadedUrl = s3Uploader.uploadFromUrl(banner.getImgUrl());
                    banner.setImgUrl(uploadedUrl);
                }

                banner.setPosted(true);
                eventBannerRepository.save(banner);

            } else if (!withinActivePeriod && banner.isPosted()) {
                log.info("게시 종료: {}", banner.getImgUrl());
                banner.setPosted(false);
                eventBannerRepository.save(banner);
            }
        }
    }

    private boolean isPublicImage(String imgUrl) {
        return imgUrl.startsWith("학교 사이트에서 배너 이미지 가져올 링크") || imgUrl.contains("public"); // 링크 나중에 넣어야 함.
    }
}
