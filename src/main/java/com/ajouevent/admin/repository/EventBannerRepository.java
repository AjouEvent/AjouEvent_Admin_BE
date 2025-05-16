package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.EventBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventBannerRepository extends JpaRepository<EventBanner, Long> {
    List<EventBanner> findByStartDateLessThanEqualAndEndDateGreaterThanEqualOrderByBannerOrderAsc(LocalDate today1, LocalDate today2);
}
