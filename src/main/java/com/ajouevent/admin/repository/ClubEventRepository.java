package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.ClubEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {
    Optional<List<ClubEvent>> findAllByIsHiddenFalse();
    Optional<List<ClubEvent>> findAllByIsHiddenTrue();
    Optional<List<ClubEvent>> findAllBySubjectAndIsHiddenFalse(String subject);
    Optional<List<ClubEvent>> findAllBySubjectAndIsHiddenTrue(String subject);
}

