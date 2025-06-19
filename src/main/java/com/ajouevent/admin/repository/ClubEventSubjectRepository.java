package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.ClubEventSubject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClubEventSubjectRepository extends JpaRepository<ClubEventSubject, Long> {
    Optional<ClubEventSubject> findByName(String name);
}
