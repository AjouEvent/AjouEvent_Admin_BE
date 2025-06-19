package com.ajouevent.admin.init;

import com.ajouevent.admin.domain.*;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.ClubEventImageRepository;
import com.ajouevent.admin.repository.ClubEventRepository;
import com.ajouevent.admin.repository.ClubEventSubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class SubjectInitializer implements CommandLineRunner {

    private final ClubEventSubjectRepository clubEventSubjectRepository;
    private final ClubEventRepository clubEventRepository;
    private final ClubEventImageRepository clubEventImageRepository;

    @Override
    public void run(String... args) {
        if (clubEventSubjectRepository.count() == 0) {
            List<String> subjects = List.of("패치노트", "기숙사", "동아리", "자유게시판", "아주대학교-일반", "소프트웨어");
            List<ClubEventSubject> entities = subjects.stream()
                    .map(name -> ClubEventSubject.builder().name(name).build())
                    .toList();
            List<ClubEventSubject> saved = clubEventSubjectRepository.saveAll(entities);

            ClubEventSubject patchNote = saved.stream()
                    .filter(s -> s.getName().equals("패치노트"))
                    .findFirst()
                    .orElseThrow(); // 절대 없을 수 없음
        }

        //더미 데이터 넣기
        if (clubEventRepository.count() == 0) {
            ClubEventSubject subject1 = clubEventSubjectRepository.findByName("패치노트")
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));
            ClubEventSubject subject2 = clubEventSubjectRepository.findByName("동아리")
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));
            ClubEventSubject subject3 = clubEventSubjectRepository.findByName("자유게시판")
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));
            ClubEventSubject subject4 = clubEventSubjectRepository.findByName("아주대학교-일반")
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));
            ClubEventSubject subject5 = clubEventSubjectRepository.findByName("기숙사")
                    .orElseThrow(() -> new ApiException(ErrorCode.CLUB_EVENT_SUBJECT_NOT_FOUND));


            ClubEvent clubEvent1 = ClubEvent.builder()
                    .title("☆2025-하계방학 2차 잔여석 신청 안내☆")
                    .content("[2025-하계방학 2차 잔여석 신청 안내]\n" +
                            "▶ 2025-하계방학 2차 잔여석 (※1차 미납인원 반영완료)\n" +
                            "▶ 잔여석 신청: [아주대학교 포털] > [Login] > [학사서비스] > [학생생활신청] > [생활관 입사신청]\n" +
                            "1) 신청대상: 입사 자격에 부합하는 하계방학 정규(1차)신청 미신청자, 불합격자\n" +
                            "2) 잔여석이 없는 건물/호실타입의 경우 선발되지 않을 수 있음\n" +
                            "3) 선발점수가 높은 순서대로 선발됨\n" +
                            "4) 2차 신청에 합격한 2025-1학기 거주자의 경우, 6/23(월)까지 입사비를 납부해야 연속 거주 가능\n" +
                            "(※6/23(월)까지 미납 시 1학기 정규퇴사일 6/24(화)까지 퇴사 후, 하계 정규입사일 6/28(토) 이후에 재입사 해야함)\n" +
                            "5) 2차신청 이후 하계방학 중도입사 신청은 개별문의 바람(※중도 입사비 참조 ☜클릭)\n" +
                            "6) 현재 하계방학 1차 합격자는 잔여석이 있더라도 건물 변경(관 이동) 불가\n" +
                            "7) 2차 신청 시 반드시 하계방학 신청 안내문 확인 [2025-하계방학 생활관 입사신청 안내] ☜(클릭)\n" +
                            "▶입사비\n" +
                            "▶고지서 출력방법\n" +
                            "- [아주대 포털] > [학사서비스] > [생활관 입사신청] > [입사신청현황] 탭 > '고지서 출력' 버튼 클릭\n" +
                            "▶문의\n" +
                            "- 학부생 031-219-2167\n" +
                            "- 대학원생/의대/간호대/법전원 031-219-2144\n" +
                            "- 이메일 dorm@ajou.ac.kr")
                    .writer("생활관운영팀")
                    .subject(subject5)
                    .url("https://www.ajouevent.com/event/62920")
                    .type(Type.SOFTWARE) // 너희 enum 값 맞춰서
                    .isHidden(false)
                    .build();

            ClubEventImage img1 = ClubEventImage.builder()
                    .url("https://www.ajou.ac.kr/_attach/ajou/editor-image/2025/06/diCkKvFRHCVRNgQzuJMDbpcbnu.png")
                    .clubEvent(clubEvent1)
                    .build();

            clubEvent1.getClubEventImageList().add(img1);
            clubEventRepository.save(clubEvent1);

            ClubEvent clubEvent2 = ClubEvent.builder()
                    .title("2025학년도 천원의 아침밥 '아침든든 아주' 시행 안내 (2025.03.04 ~ )")
                    .content("")
                    .writer("학생지원팀")
                    .subject(subject4)
                    .url("https://www.ajouevent.com/event/60386")
                    .type(Type.SOFTWARE)
                    .isHidden(false)
                    .build();

            ClubEventImage img2 = ClubEventImage.builder()
                    .url("https://www.ajou.ac.kr/_attach/ajou/editor-image/2025/02/UcrWhdMMlypqrQedBSeMpPzYCX.jpg")
                    .clubEvent(clubEvent2)
                    .build();

            clubEvent2.getClubEventImageList().add(img2);
            clubEventRepository.save(clubEvent2);

            ClubEvent clubEvent3 = ClubEvent.builder()
                    .title("[예비군연대] 25년 학부생 예비군훈련(기본훈련) 안내")
                    .content("안녕하세요? 아주대학교 예비군연대입니다.\n" +
                            "예비군법 제 6조 1항 및 공직선거법 제 33조 1항에 따라 대선으로 인하여 9월로 순연된\n" +
                            "학부생 예비군훈련(기본훈련) 편성일정 및 관련 안내사항 공지드립니다.\n" +
                            "자세한 학과별 편성일정 및 주요사항은 학교 예비군연대 홈페이지 공지사항을 참고바랍니다.\n" +
                            "참고로 2학기 졸업·휴학·수료·초과학기자를 예상 고려하여\n" +
                            "단과대학(과)별 편성 예비군의 최고학년을 훈련일정 후반으로 편성하였으니 양해바랍니다.\n" +
                            "↳ 일반 4학년, 건축학&의학 5학년, 약학 6학년 대상\n" +
                            "(훈련 D-30~D-23전 소집통지서 발송필요 등 행정소요기간 고려)\n" +
                            "훈련일정 변경에 따른 문의사항이 있으시면 유선 질의 또는 방문하여 주시면 친절히 답변드리겠습니다.\n" +
                            "* 아주대학교 예비군연대(학군단 1층 104호)☎ 031-219-2218 ~ 9")
                    .writer("예비군연대")
                    .subject(subject4)
                    .url("https://www.ajouevent.com/event/62932")
                    .type(Type.SOFTWARE)
                    .isHidden(false)
                    .build();

            ClubEventImage img3 = ClubEventImage.builder()
                    .url("https://yebigun.ajou.ac.kr/_attach/ajou/editor-image/2025/06/sbSoWslhyMzHiNHKCvkkcTWjEG.jpg")
                    .clubEvent(clubEvent3)
                    .build();

            clubEvent3.getClubEventImageList().add(img3);
            clubEventRepository.save(clubEvent3);

            ClubEvent clubEvent4 = ClubEvent.builder()
                    .title("[아주대학교 총학생회]제7회 경기도민 정책축제 '찾아가는 정책 카페'(6월 19일(목) 13시~15시)")
                    .content("")
                    .writer("학생지원팀")
                    .subject(subject4)
                    .url("https://www.ajouevent.com/event/62935")
                    .type(Type.SOFTWARE)
                    .isHidden(false)
                    .build();

            ClubEventImage img4 = ClubEventImage.builder()
                    .url("https://www.ajou.ac.kr/_attach/ajou/editor-image/2025/06/ugJIwriMCQYgnHXnDiQhNsItmj.jpg")
                    .clubEvent(clubEvent4)
                    .build();

            clubEvent4.getClubEventImageList().add(img4);
            clubEventRepository.save(clubEvent4);
        }
    }
}
