package com.ajouevent.admin.repository;

import com.ajouevent.admin.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);

    @Query("SELECT m FROM Member m WHERE m.id NOT IN (SELECT b.member.id FROM Blacklist b)")
    List<Member> findAllNotBlacklisted();

}
