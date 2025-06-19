package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.ClubEvent;
import com.ajouevent.admin.domain.ClubEventImage;
import com.ajouevent.admin.domain.ClubEventSubject;
import com.ajouevent.admin.dto.request.ClubEventCreateRequest;
import com.ajouevent.admin.dto.response.ClubEventListResponse;
import com.ajouevent.admin.dto.response.ClubEventResponse;
import com.ajouevent.admin.dto.response.EventSubjectListResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.ClubEventImageRepository;
import com.ajouevent.admin.repository.ClubEventRepository;
import com.ajouevent.admin.repository.ClubEventSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClubEventService {

    private final ClubEventRepository clubEventRepository;
    private final ClubEventSubjectRepository clubEventSubjectRepository;

    private static final String UPLOAD_DIR = Paths.get(System.getProperty("user.dir"), "src", "main", "resources", "static", "uploads").toString();


//    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final String IMAGE_URL_PREFIX = "/uploads/";


    @Transactional
    public void create(ClubEventCreateRequest request, List<MultipartFile> images) {
//        if (!"patchNote".equalsIgnoreCase(request.getSubject())) {
//            throw new ApiException(ErrorCode.INVALID_SUBJECT);
//        }
        ClubEventSubject subject = clubEventSubjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));

        ClubEvent event = ClubEvent.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .writer(request.getWriter())
                .subject(subject)
                .url(request.getUrl())
                .type(request.getType())
                .isHidden(false)
                .build();

        // 이미지 처리
        if (images != null && !images.isEmpty()) {
            for (MultipartFile image : images) {
                try {
                    String originalFileName = image.getOriginalFilename();
                    long imageSize = image.getSize();
                    String contentType = image.getContentType();
                    log.info("Received image: name={}, size={} bytes, contentType={}",
                            originalFileName, imageSize, contentType);

                    String storedFileName = UUID.randomUUID() + "_" + originalFileName;
                    Path savePath = Paths.get(UPLOAD_DIR, storedFileName); // 경로 생성 안전하게 수정

                    // 디렉토리 없으면 생성
                    Files.createDirectories(savePath.getParent());

                    image.transferTo(savePath.toFile());

                    long savedSize = Files.size(savePath);
                    log.info("Saved image to {}, size on disk = {} bytes", savePath, savedSize);

                    // 이미지 엔티티 생성 및 연관 설정
                    ClubEventImage clubEventImage = new ClubEventImage();
                    clubEventImage.setUrl(IMAGE_URL_PREFIX + storedFileName);
                    clubEventImage.setClubEvent(event);
                    event.getClubEventImageList().add(clubEventImage);

                } catch (IOException e) {
                    log.error("File save failed for {}: {}", image.getOriginalFilename(), e.getMessage(), e);
                    throw new ApiException(ErrorCode.FILE_UPLOAD_FAILED);
                }
            }
        }


        clubEventRepository.save(event); // cascade에 의해 이미지도 저장됨
    }


    @Transactional(readOnly = true)
    public ClubEventListResponse findAll(Long subjectId) {
        List<ClubEvent> events;

        if (subjectId == null) {
            events = clubEventRepository.findAll();
        } else {
            ClubEventSubject subject = clubEventSubjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));
            events = clubEventRepository.findBySubject(subject);
        }

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event), event.getSubject()))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventListResponse findAllVisible(Long subjectId) {
        List<ClubEvent> events;

        if (subjectId == null) {
            events = clubEventRepository.findAllByIsHiddenFalse();
        } else {
            ClubEventSubject subject = clubEventSubjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));

            events = clubEventRepository.findBySubjectAndIsHiddenFalse(subject);
        }

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event), event.getSubject()))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventListResponse findAllHidden(Long subjectId) {
        List<ClubEvent> events;

        if (subjectId == null) {
            events = clubEventRepository.findAllByIsHiddenTrue();
        } else {
            ClubEventSubject subject = clubEventSubjectRepository.findById(subjectId)
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));

            events = clubEventRepository.findBySubjectAndIsHiddenTrue(subject);
        }

        List<ClubEventResponse> responses = events.stream()
                .map(event -> ClubEventResponse.from(event, extractImageUrls(event), event.getSubject()))
                .toList();

        return new ClubEventListResponse(responses);
    }

    @Transactional(readOnly = true)
    public ClubEventResponse findById(Long eventId) {
        ClubEvent event = clubEventRepository.findById(eventId)
                .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_NOT_FOUND));

        return ClubEventResponse.from(event, extractImageUrls(event), event.getSubject());
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

    @Transactional(readOnly = true)
    public EventSubjectListResponse findAllSubjects() {
        List<ClubEventSubject> subjects = clubEventSubjectRepository.findAll();
        return EventSubjectListResponse.from(subjects);
    }

    private List<String> extractImageUrls(ClubEvent event) {
        return event.getClubEventImageList().stream()
                .map(ClubEventImage::getUrl)
                .toList();
    }
}

