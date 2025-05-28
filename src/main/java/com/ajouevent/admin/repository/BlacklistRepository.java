package com.ajouevent.admin.repository;

import java.util.Optional;
import com.ajouevent.admin.domain.Blacklist;
import com.ajouevent.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlacklistRepository extends JpaRepository<Blacklist, Long> {
    boolean existsByMember(Member member);
    Optional<Blacklist> findByMember(Member member);
    void deleteByMember(Member member);
}

