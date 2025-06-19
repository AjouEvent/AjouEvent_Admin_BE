package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.ClubEvent;
import com.ajouevent.admin.domain.ClubEventSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClubEventRepository extends JpaRepository<ClubEvent, Long> {
    List<ClubEvent> findAllByIsHiddenFalse();
    List<ClubEvent> findAllByIsHiddenTrue();
    List<ClubEvent> findBySubjectAndIsHiddenFalse(ClubEventSubject subject);
    List<ClubEvent> findBySubjectAndIsHiddenTrue(ClubEventSubject subject);
    List<ClubEvent> findBySubject(ClubEventSubject subject);
}

