package com.ajouevent.admin.repository;


import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.domain.MemberPermission;
import com.ajouevent.admin.domain.PermissionType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberPermissionRepository extends JpaRepository<MemberPermission, Long> {
    List<MemberPermission> findByMember(Member member);
    Optional<MemberPermission> findByMemberAndPermissionType(Member member, PermissionType permissionType);
}

