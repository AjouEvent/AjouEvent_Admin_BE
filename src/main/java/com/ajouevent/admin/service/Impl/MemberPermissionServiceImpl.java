package com.ajouevent.admin.service.Impl;

import com.ajouevent.admin.dto.response.MemberListResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.service.MemberPermissionService;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.domain.MemberPermission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.domain.RoleType;
import com.ajouevent.admin.dto.response.MemberResponse;
import com.ajouevent.admin.repository.MemberPermissionRepository;
import com.ajouevent.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberPermissionServiceImpl implements MemberPermissionService {

    private final MemberRepository memberRepository;
    private final MemberPermissionRepository memberPermissionRepository;

    @Override
    public MemberListResponse getAllMembers() {
        List<Member> members = memberRepository.findAll();

        List<MemberResponse> responses = members.stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());

        return MemberListResponse.from(responses);
    }

    @Override
    public MemberListResponse getMembersByRole(RoleType role) {
        List<Member> members = memberRepository.findAll().stream()
                .filter(member -> member.getRole() == role)
                .collect(Collectors.toList());

        List<MemberResponse> responses = members.stream()
                .map(this::toMemberResponse)
                .collect(Collectors.toList());

        return MemberListResponse.from(responses);
    }

    @Override
    public void changeRole(Long memberId, RoleType newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        // 기존 오버라이드 권한 제거 (both in DB and in member entity)
        List<MemberPermission> toRemove = memberPermissionRepository.findByMember(member);

        // JPA 엔티티 컬렉션에서 remove (orphanRemoval 발동)
        toRemove.forEach(member.getOverriddenPermissions()::remove);

        // 최종적으로 deleteAll로 한 번에 정리 (DB 반영 확실)
        memberPermissionRepository.deleteAll(toRemove);

        member.setRole(newRole);
        memberRepository.save(member);
    }


    @Override
    public void grantPermission(Long memberId, PermissionType permissionType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        boolean alreadyGranted = memberPermissionRepository
                .findByMemberAndPermissionType(member, permissionType)
                .isPresent();

        if (alreadyGranted) {
            throw new ApiException(ErrorCode.PERMISSION_ALREADY_GRANTED);
        }

        MemberPermission permission = new MemberPermission();
        permission.setMember(member);
        permission.setPermissionType(permissionType);
        member.getOverriddenPermissions().add(permission); // maintain bidirectional integrity
        memberPermissionRepository.save(permission);
    }

    @Override
    public void revokePermission(Long memberId, PermissionType permissionType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        MemberPermission permission = memberPermissionRepository
                .findByMemberAndPermissionType(member, permissionType)
                .orElseThrow(() -> new ApiException(ErrorCode.PERMISSION_NOT_FOUND));

        member.getOverriddenPermissions().remove(permission); // maintain bidirectional integrity
        memberPermissionRepository.delete(permission);
    }

    //member -> memberRespons 변환기
    private MemberResponse toMemberResponse(Member member) {
        Set<PermissionType> effectivePermissions = new HashSet<>(member.getRole().getDefaultPermissions());

        List<MemberPermission> overrides = memberPermissionRepository.findByMember(member);
        for (MemberPermission mp : overrides) {
            effectivePermissions.add(mp.getPermissionType());
        }

        return MemberResponse.from(member, effectivePermissions);
    }
}
