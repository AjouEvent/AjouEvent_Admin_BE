package com.ajouevent.admin.controller;

import com.ajouevent.admin.dto.request.ClubEventCreateRequest;
import com.ajouevent.admin.dto.response.ClubEventListResponse;
import com.ajouevent.admin.dto.response.ClubEventResponse;
import com.ajouevent.admin.service.ClubEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.Map;

import static java.util.Collections.emptyMap;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/club-events")

public class ClubEventController {

    private final ClubEventService clubEventService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(
            @RequestPart("request") ClubEventCreateRequest request,
            @RequestPart(value = "images", required = false) List<MultipartFile> images
    ) {
        clubEventService.create(request, images);
        return ResponseEntity.ok(emptyMap());
    }

    @GetMapping
    public ResponseEntity<ClubEventListResponse> findAll() {
        return ResponseEntity.ok(clubEventService.findAll());
    }

    @GetMapping("/visible")
    public ResponseEntity<ClubEventListResponse> findVisible(@RequestParam(required = false) String subject) {
        return ResponseEntity.ok(clubEventService.findAllVisible(subject));
    }

    @GetMapping("/hidden")
    public ResponseEntity<ClubEventListResponse> findHidden(@RequestParam(required = false) String subject) {
        return ResponseEntity.ok(clubEventService.findAllHidden(subject));
    }

    @GetMapping("/{eventId}")
    public ResponseEntity<ClubEventResponse> findById(@PathVariable Long eventId) {
        return ResponseEntity.ok(clubEventService.findById(eventId));
    }

    @PatchMapping("/{eventId}/hide")
    public ResponseEntity<Map<String, Object>> hide(@PathVariable Long eventId) {
        clubEventService.hide(eventId);
        return ResponseEntity.ok(emptyMap());
    }

    @PatchMapping("/{eventId}/show")
    public ResponseEntity<Map<String, Object>> unhide(@PathVariable Long eventId) {
        clubEventService.unhide(eventId);
        return ResponseEntity.ok(emptyMap());
    }
}