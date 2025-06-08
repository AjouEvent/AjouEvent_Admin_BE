package com.ajouevent.admin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class InquiryListResponse {
    private List<InquiryResponse> inquiries;
}
