package com.ajouevent.admin.service;

import com.ajouevent.admin.dto.request.InquiryRequest;
import com.ajouevent.admin.dto.response.InquiryListResponse;
import com.ajouevent.admin.dto.response.InquiryResponse;

public interface InquiryService {

    void submitInquiry(InquiryRequest request);

    InquiryListResponse getAllInquiries(); // 관리자용 전체 조회

    InquiryResponse getInquiry(Long id); // 단건 조회

    void answerInquiry(Long id, String answer);

    void rejectInquiry(Long id, String answer);
}

