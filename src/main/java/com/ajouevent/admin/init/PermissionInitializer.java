package com.ajouevent.admin.init;

import com.ajouevent.admin.domain.Permission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.domain.RoleType;
import com.ajouevent.admin.domain.Member;
import com.ajouevent.admin.repository.MemberRepository;
import com.ajouevent.admin.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;
    private final MemberRepository memberRepository;


    @Override
    public void run(String... args) {

        for (PermissionType type : PermissionType.values()) {
            boolean exists = permissionRepository.findByType(type).isPresent();
//            System.out.println("🔍 " + type.name() + " exists? " + exists);

            if (!exists) {
//                System.out.println("💾 Saving permission: " + type.name());
                permissionRepository.save(
                        Permission.builder()
                                .type(type)
                                .description(type.getDescription())
                                .build()
                );
            }
        }

//        // 2. Member 더미 삽입
//        if (memberRepository.count() == 2) { // 안 겹치게
//            Member m1 = Member.builder()
//                    .name("최민준")
//                    .email("alswns@ajou.ac.kr")
//                    .role(RoleType.USER)
//                    .build();
//
//            Member m2 = Member.builder()
//                    .name("박병언")
//                    .email("quddjs@ajou.ac.kr")
//                    .role(RoleType.LEADER)
//                    .build();
//
//            Member m3 = Member.builder()
//                    .name("이찬주")
//                    .email("ckswn@ajou.ac.kr")
//                    .role(RoleType.LEADER)
//                    .build();
//
//            Member m4 = Member.builder()
//                    .name("윤석찬")
//                    .email("tjrcks@ajou.ac.kr")
//                    .role(RoleType.LEADER)
//                    .build();
//
//            memberRepository.saveAll(List.of(m1, m2, m3, m4));
////            System.out.println("👤 Members saved with default permissions.");
//        }


    }
}
