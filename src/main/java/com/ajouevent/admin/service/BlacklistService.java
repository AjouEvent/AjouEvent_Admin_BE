package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.Blacklist;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.domain.MemberPermission;
import com.ajouevent.admin.dto.response.BlacklistListResponse;
import com.ajouevent.admin.dto.response.MemberListResponse;
import com.ajouevent.admin.dto.response.MemberResponse;
import com.ajouevent.admin.repository.BlacklistRepository;
import com.ajouevent.admin.dto.response.BlacklistResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.MemberRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlacklistService{

    private final MemberRepository memberRepository;
    private final BlacklistRepository blacklistRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void register(Long memberId, String reason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (blacklistRepository.existsByMember(member)) {
            throw new ApiException(ErrorCode.ALREADY_BLACKLISTED);
        }

        member.getOverriddenPermissions().clear();
        memberRepository.save(member);

        entityManager.flush();
        entityManager.refresh(member);

        Blacklist entry = Blacklist.builder()
                .reason(reason)
                .member(member)
                .build();

        blacklistRepository.save(entry);
    }

    @Transactional
    public void revoke(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (!blacklistRepository.existsByMember(member)) {
            throw new ApiException(ErrorCode.BLACKLIST_ENTRY_NOT_FOUND);
        }

        member.setPermissionsByRole(member.getRole());
        memberRepository.save(member);
        blacklistRepository.deleteByMember(member);
    }

    public BlacklistListResponse getAll() {
        List<BlacklistResponse> list = blacklistRepository.findAll().stream()
                .map(entry -> BlacklistResponse.builder()
                        .memberId(entry.getMember().getId())
                        .memberName(entry.getMember().getName())
                        .reason(entry.getReason())
                        .createdAt(entry.getCreatedAt())
                        .build())
                .toList();

        return new BlacklistListResponse(list);
    }


    @Transactional(readOnly = true)
    public MemberListResponse getNonBlacklistedMembers() {
        List<Member> members = memberRepository.findAllNotBlacklisted();

        List<MemberResponse> result = members.stream()
                .map(m -> MemberResponse.from(
                        m,
                        m.getOverriddenPermissions().stream()
                                .map(MemberPermission::getPermissionType)
                                .collect(Collectors.toSet())
                ))
                .toList();

        return MemberListResponse.from(result);
    }
}
