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

        // 2. Member 더미 삽입
        if (memberRepository.count() == 0) { // 안 겹치게
            Member m1 = Member.builder()
                    .name("김철수")
                    .email("chulsoo@ajou.ac.kr")
                    .role(RoleType.USER)
                    .build();

            Member m2 = Member.builder()
                    .name("박영희")
                    .email("younghee@ajou.ac.kr")
                    .role(RoleType.LEADER)
                    .build();

            memberRepository.saveAll(List.of(m1, m2));
//            System.out.println("👤 Members saved with default permissions.");
        } else {
//            System.out.println("ℹ️ Members already exist, skipping insert.");
        }
    }




}
