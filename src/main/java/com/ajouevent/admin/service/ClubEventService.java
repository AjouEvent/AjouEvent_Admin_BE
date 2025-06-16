package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.ClubEvent;
import com.ajouevent.admin.domain.ClubEventImage;
import com.ajouevent.admin.dto.request.ClubEventCreateRequest;
import com.ajouevent.admin.dto.response.ClubEventListResponse;
import com.ajouevent.admin.dto.response.ClubEventResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.ClubEventImageRepository;
import com.ajouevent.admin.repository.ClubEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClubEventService {

    private final ClubEventRepository clubEventRepository;
    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final String IMAGE_URL_PREFIX = "/uploads/";


    @Transactional
    public void create(ClubEventCreateRequest request, List<MultipartFile> images) {
//        if (!"patchNote".equalsIgnoreCase(request.getSubject())) {
//            throw new ApiException(ErrorCode.INVALID_SUBJECT);
//        }

        ClubEvent event = ClubEvent.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .subject(request.getSubject())
                .url(request.getUrl())
                .type(request.getType())
                .isHidden(false)
                .build();

        // 이미지 처리
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                try {
                    String originalFileName = image.getOriginalFilename();
                    String storedFileName = UUID.randomUUID() + "_" + originalFileName;
                    Path savePath = Paths.get(UPLOAD_DIR + storedFileName);

                    // 디렉토리 없으면 생성
                    Files.createDirectories(savePath.getParent());
                    image.transferTo(savePath.toFile());

                    // 이미지 엔티티 생성 및 연관 설정
                    ClubEventImage clubEventImage = new ClubEventImage();
                    clubEventImage.setUrl(IMAGE_URL_PREFIX + storedFileName);
                    clubEventImage.setClubEvent(event);

                    event.getClubEventImageList().add(clubEventImage); // 연관 설정
                } catch (IOException e) {
                    throw new ApiException(ErrorCode.FILE_UPLOAD_FAILED);
                }
            }
        }

        clubEventRepository.save(event); // cascade에 의해 이미지도 저장됨
    }


    @Transactional(readOnly = true)
    public ClubEventListResponse findAll() {
        List<ClubEvent> events = clubEventRepository.findAll();

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event)))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventListResponse findAllVisible(String subject) {
        List<ClubEvent> events = subject == null ?
                clubEventRepository.findAllByIsHiddenFalse()
                        .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND)) :
                clubEventRepository.findAllBySubjectAndIsHiddenFalse(subject)
                        .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event)))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventListResponse findAllHidden(String subject) {
        List<ClubEvent> events = subject == null ?
                clubEventRepository.findAllByIsHiddenTrue()
                        .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND)) :
                clubEventRepository.findAllBySubjectAndIsHiddenTrue(subject)
                        .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event)))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventResponse findById(Long eventId) {
        ClubEvent event = clubEventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));

        return ClubEventResponse.from(event, extractImageUrls(event));
    }

    @Transactional
    public void hide(Long eventId) {
        ClubEvent event = clubEventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));
        event.hide();
    }

    @Transactional
    public void unhide(Long eventId) {
        ClubEvent event = clubEventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));
        event.unhide();
    }

    private List<String> extractImageUrls(ClubEvent event) {
        return event.getClubEventImageList().stream()
                .map(ClubEventImage::getUrl)
                .toList();
    }
}

