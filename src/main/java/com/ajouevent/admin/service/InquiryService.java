package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.Inquiry;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.dto.request.InquiryRequest;
import com.ajouevent.admin.dto.response.InquiryListResponse;
import com.ajouevent.admin.dto.response.InquiryResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.InquiryRepository;
import com.ajouevent.admin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final MemberRepository memberRepository;

    public void submitInquiry(InquiryRequest request) {
        Member member = memberRepository.findById(request.getMemberId())
                .orElseThrow(() -> new ApiException(ErrorCode.MEMBER_NOT_FOUND));

        Inquiry inquiry = Inquiry.builder()
                .member(member)
                .title(request.getTitle())
                .content(request.getContent())
                .build();

        inquiryRepository.save(inquiry);
    }

    public InquiryListResponse getAllInquiries() {
        List<InquiryResponse> list = inquiryRepository.findAll().stream()
                .map(inquiry -> InquiryResponse.from(inquiry, inquiry.getMember().getName()))
                .toList();

        return new InquiryListResponse(list);
    }

    public InquiryResponse getInquiry(Long id) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.INQUIRY_NOT_FOUND));

        return InquiryResponse.from(inquiry, inquiry.getMember().getName());
    }

    @Transactional
    public void answerInquiry(Long id, String answer) {
        Inquiry inquiry = inquiryRepository.findById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.INQUIRY_NOT_FOUND));

        inquiry.answer(answer);
        inquiryRepository.save(inquiry);
    }
}
