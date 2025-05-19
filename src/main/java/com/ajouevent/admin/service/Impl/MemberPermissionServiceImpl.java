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

        // 역할 변경
        member.setRole(newRole);

        // 기존 오버라이드 권한 모두 제거 (엔티티 자체에서)
        member.getOverriddenPermissions().clear();

        // 새 역할의 기본 권한 추가
        for (PermissionType permissionType : newRole.getDefaultPermissions()) {
            member.addPermission(permissionType);
        }

        // 한 번에 저장 (cascade로 함께 반영됨)
        memberRepository.save(member);
    }


    @Override
    public void grantPermission(Long memberId, PermissionType permissionType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.hasPermission(permissionType)) {
            throw new ApiException(ErrorCode.PERMISSION_ALREADY_GRANTED);
        }

        member.addPermission(permissionType);
        memberRepository.save(member); // cascade 적용됨
    }

    @Override
    public void revokePermission(Long memberId, PermissionType permissionType) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.hasPermission(permissionType)) {
            throw new ApiException(ErrorCode.PERMISSION_NOT_FOUND);
        }

        member.removePermission(permissionType);
        memberRepository.save(member); // 삭제 반영됨 (orphanRemoval)
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
