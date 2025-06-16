package com.ajouevent.admin.init;

import com.ajouevent.admin.domain.*;
import com.ajouevent.admin.repository.ClubEventImageRepository;
import com.ajouevent.admin.repository.ClubEventRepository;
import com.ajouevent.admin.repository.MemberRepository;
import com.ajouevent.admin.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final MemberRepository memberRepository;

    private final ClubEventRepository clubEventRepository;
    private final ClubEventImageRepository clubEventImageRepository;

    @Override
    public void run(String... args) {
        for (PermissionType type : PermissionType.values()) {
            boolean exists = permissionRepository.findByType(type).isPresent();
            if (!exists) {
                permissionRepository.save(
                        Permission.builder()
                                .type(type)
                                .description(type.getDescription())
                                .build()
                );
            }
        }

        if (memberRepository.count() == 0) {
            Member m1 = Member.builder()
                    .name("최민준")
                    .email("alswns@ajou.ac.kr")
                    .role(RoleType.USER)
                    .build();

            Member m2 = Member.builder()
                    .name("박병언")
                    .email("quddjs@ajou.ac.kr")
                    .role(RoleType.USER)
                    .build();

            Member m3 = Member.builder()
                    .name("이찬주")
                    .email("ckswn@ajou.ac.kr")
                    .role(RoleType.USER)
                    .build();

            Member m4 = Member.builder()
                    .name("박상준")
                    .email("tkdwns@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();

            Member m5 = Member.builder()
                    .name("윤석찬")
                    .email("tjrcks@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();
            Member m6 = Member.builder()
                    .name("심재엽")
                    .email("woduq@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();
            Member m7 = Member.builder()
                    .name("이장원")
                    .email("wkddnjs@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();
            Member m8 = Member.builder()
                    .name("이은정")
                    .email("dmswjd@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();

            memberRepository.saveAll(List.of(m1, m2, m3,m4,m5,m6,m7,m8));
        }

        if (clubEventRepository.count() == 0) {
            ClubEvent clubEvent = ClubEvent.builder()
                    .title("테스트 이벤트")
                    .content("테스트 이벤트 내용")
                    .writer("관리자")
                    .subject("patchNote")
                    .url("https://ajou.ac.kr")
                    .type(Type.SOFTWARE) // 너희 enum 값 맞춰서
                    .isHidden(false)
                    .clubEventImageList(new ArrayList<>()) // 연관 리스트 직접 초기화!
                    .build();

            ClubEventImage img1 = ClubEventImage.builder()
                    .url("/uploads/test1.png")
                    .clubEvent(clubEvent)
                    .build();

            ClubEventImage img2 = ClubEventImage.builder()
                    .url("/uploads/test2.png")
                    .clubEvent(clubEvent)
                    .build();

            // 양방향 연관관계 설정
            clubEvent.getClubEventImageList().add(img1);
            clubEvent.getClubEventImageList().add(img2);

            // 이벤트 저장 (cascade = PERSIST라 이미지까지 한 번에 저장됨)
            clubEventRepository.save(clubEvent);
        }
    }
}
