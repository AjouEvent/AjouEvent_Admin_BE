package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.dto.response.BlacklistListResponse;

public interface BlacklistService {

    void register(Long memberId, String reason);

    void revoke(Long memberId);

    BlacklistListResponse getAll();

    boolean isBlacklisted(Member member);
}
