package com.ajouevent.admin.service.Impl;

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
import com.ajouevent.admin.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final MemberRepository memberRepository;
    private final BlacklistRepository blacklistRepository;

    @Override
    @Transactional
    public void register(Long memberId, String reason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (member.isBlacklisted()) {
            throw new ApiException(ErrorCode.ALREADY_BLACKLISTED);
        }

        member.getOverriddenPermissions().clear();

        Blacklist entry = Blacklist.builder()
                .reason(reason)
                .build();

        member.assignBlacklist(entry); // 양방향 연관관계 설정

        // CascadeType.ALL + orphanRemoval=true 이면 save 없어도 되지만 명시적으로 해주면 안전
        blacklistRepository.save(entry);
    }

    @Override
    @Transactional
    public void revoke(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (!member.isBlacklisted()) {
            throw new ApiException(ErrorCode.BLACKLIST_ENTRY_NOT_FOUND);
        }

        member.revokeBlacklist(); // 양방향 연관관계 제거

        member.setPermissionsByRole(member.getRole());
        memberRepository.save(member); // blacklist는 orphanRemoval로 자동 삭제됨
    }

    @Override
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



    @Override
    public boolean isBlacklisted(Member member) {
        return blacklistRepository.existsByMember(member);
    }

    @Override
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
