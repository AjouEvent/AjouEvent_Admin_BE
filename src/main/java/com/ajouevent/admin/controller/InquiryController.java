package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.InquiryAnswerRequest;
import com.ajouevent.admin.dto.request.InquiryRequest;
import com.ajouevent.admin.dto.response.InquiryListResponse;
import com.ajouevent.admin.dto.response.InquiryResponse;
import com.ajouevent.admin.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static java.util.Collections.emptyMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class InquiryController {

    private final InquiryService inquiryService;

    //사용자용
    @PostMapping("/inquiries")
    public ResponseEntity<Map<String, Object>> submitInquiry(@RequestBody InquiryRequest request) {
        inquiryService.submitInquiry(request);
        return ResponseEntity.ok(emptyMap());
    }

    @GetMapping("/admin/inquiries")
    public ResponseEntity<InquiryListResponse> getAllInquiries() {
        return ResponseEntity.ok(inquiryService.getAllInquiries());
    }

    @GetMapping("/admin/inquiries/{id}")
    public ResponseEntity<InquiryResponse> getInquiry(@PathVariable Long id) {
        return ResponseEntity.ok(inquiryService.getInquiry(id));
    }

    @PatchMapping("/admin/inquiries/{id}/answer")
    public ResponseEntity<Map<String, Object>> answerInquiry(@PathVariable Long id,
                                              @RequestBody InquiryAnswerRequest request) {
        inquiryService.answerInquiry(id, request.getAnswer());
        return ResponseEntity.ok(emptyMap());
    }
}

