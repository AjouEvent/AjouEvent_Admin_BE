package com.ajouevent.admin.service.Impl;

import com.ajouevent.admin.domain.Blacklist;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.dto.response.BlacklistListResponse;
import com.ajouevent.admin.dto.response.BlacklistRepository;
import com.ajouevent.admin.dto.response.BlacklistResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.MemberRepository;
import com.ajouevent.admin.service.BlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BlacklistServiceImpl implements BlacklistService {

    private final MemberRepository memberRepository;
    private final BlacklistRepository blacklistRepository;

    @Override
    public void register(Long memberId, String reason) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        if (blacklistRepository.existsByMember(member)) {
            throw new ApiException(ErrorCode.ALREADY_BLACKLISTED);
        }

        member.getOverriddenPermissions().clear();

        Blacklist entry = Blacklist.builder()
                .member(member)
                .reason(reason)
                .build();

        blacklistRepository.save(entry);
    }

    @Override
    public void revoke(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        boolean exists = blacklistRepository.existsByMember(member);
        if (!exists) {
            throw new ApiException(ErrorCode.BLACKLIST_ENTRY_NOT_FOUND);
        }

        blacklistRepository.deleteByMember(member);
        // 역할 기반 권한 재부여
        member.setPermissionsByRole(member.getRole());
        memberRepository.save(member);
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
}
