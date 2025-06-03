package com.ajouevent.admin.service;

import com.ajouevent.admin.dto.request.PermissionUpdateRequest;
import com.ajouevent.admin.dto.response.MemberListResponse;
import com.ajouevent.admin.dto.response.PermissionUpdateResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.domain.MemberPermission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.domain.RoleType;
import com.ajouevent.admin.dto.response.MemberResponse;
import com.ajouevent.admin.repository.MemberPermissionRepository;
import com.ajouevent.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberPermissionService {

    private final MemberRepository memberRepository;
    private final MemberPermissionRepository memberPermissionRepository;


    public MemberListResponse getAllMembers() {
        List<Member> members = memberRepository.findAll();
        return toMemberResponse(members);
    }

    public MemberListResponse getMembersByRole(RoleType role) {
        List<Member> members = memberRepository.findAll().stream()
                .filter(member -> member.getRole() == role)
                .toList();

        return toMemberResponse(members);
    }

    public void changeRole(Long memberId, RoleType newRole) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        member.getOverriddenPermissions().clear();
        member.setPermissionsByRole(newRole);
        member.setRole(newRole);
        // 한 번에 저장 (cascade로 함께 반영됨)
        memberRepository.save(member);
    }

    @Transactional
    public PermissionUpdateResponse updateMemberPermissions(Long memberId, PermissionUpdateRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Set<PermissionType> before = member.getOverriddenPermissions().stream()
                .map(MemberPermission::getPermissionType)
                .collect(Collectors.toSet());

        member.updatePermissions(request.getPermissions());

        Set<PermissionType> after = request.getPermissions();

        Set<PermissionType> added = new HashSet<>(after);
        added.removeAll(before);

        Set<PermissionType> removed = new HashSet<>(before);
        removed.removeAll(after);

        return PermissionUpdateResponse.builder()
                .memberId(member.getId())
                .added(added)
                .removed(removed)
                .build();
    }

    private MemberListResponse toMemberResponse(List<Member> members){
        List<MemberResponse> responses = members.stream()
                .map(member -> {
                    Set<PermissionType> effectivePermissions = memberPermissionRepository.findByMember(member).stream()
                            .map(MemberPermission::getPermissionType)
                            .collect(Collectors.toSet());
                    return MemberResponse.from(member, effectivePermissions);
                })
                .toList();
        return MemberListResponse.from(responses);
    }
}
