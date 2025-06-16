package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.ClubEvent;
import com.ajouevent.admin.domain.ClubEventImage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClubEventImageRepository extends JpaRepository<ClubEventImage, Long> {
    Optional<List<ClubEventImage>> findAllByClubEvent(ClubEvent clubEvent);
}