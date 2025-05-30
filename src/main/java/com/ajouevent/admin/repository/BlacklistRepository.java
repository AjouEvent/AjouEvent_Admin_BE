package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.Blacklist;
import com.ajouevent.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    boolean existsByMember(Member member);
    void deleteByMember(Member member);
}

