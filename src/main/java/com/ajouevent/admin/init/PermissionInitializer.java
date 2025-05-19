package com.ajouevent.admin.init;

import com.ajouevent.admin.domain.Permission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.repository.PermissionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @PostConstruct
    public void debug() {
        System.out.println("🔥 PermissionInitializer 실행됨");
    }

    @Override
    public void run(String... args) {
        for (PermissionType type : PermissionType.values()) {
            boolean exists = permissionRepository.findByType(type).isPresent();
            System.out.println("🔍 " + type.name() + " exists? " + exists);

            if (!exists) {
                System.out.println("💾 Saving permission: " + type.name());
                permissionRepository.save(
                        Permission.builder()
                                .type(type)
                                .description(type.getDescription())
                                .build()
                );
            }
        }
    }

}
