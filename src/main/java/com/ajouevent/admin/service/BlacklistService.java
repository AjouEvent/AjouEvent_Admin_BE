package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.dto.response.BlacklistListResponse;
import com.ajouevent.admin.dto.response.MemberListResponse;

public interface BlacklistService {

    void register(Long memberId, String reason);

    void revoke(Long memberId);

    BlacklistListResponse getAll();

    boolean isBlacklisted(Member member);

    public MemberListResponse getNonBlacklistedMembers();
}
