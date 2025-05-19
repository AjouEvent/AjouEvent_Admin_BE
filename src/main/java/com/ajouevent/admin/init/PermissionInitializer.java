package com.ajouevent.admin.init;

import com.ajouevent.admin.domain.Permission;
import com.ajouevent.admin.domain.PermissionType;
import com.ajouevent.admin.repository.PermissionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissionInitializer implements CommandLineRunner {

    private final PermissionRepository permissionRepository;

    @Override
    public void run(String... args) {
        for (PermissionType type : PermissionType.values()) {
            permissionRepository.findByType(type)
                    .orElseGet(() -> permissionRepository.save(
                            Permission.builder()
                                    .type(type)
                                    .description(type.getDescription())
                                    .build()
                    ));
        }
    }
}
