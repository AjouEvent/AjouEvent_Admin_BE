package com.ajouevent.admin.service;

import com.ajouevent.admin.domain.AdminUser;
import com.ajouevent.admin.dto.request.LoginRequest;
import com.ajouevent.admin.dto.request.SignUpRequest;
import com.ajouevent.admin.dto.response.AdminAuthResponse;
import com.ajouevent.admin.exception.ApiException;
import com.ajouevent.admin.exception.ErrorCode;
import com.ajouevent.admin.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminUserServiceImpl implements AdminUserService {

    private final AdminUserRepository adminUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminAuthResponse signUp(SignUpRequest request) {
        if (adminUserRepository.existsByEmail(request.getEmail())) {
            throw new ApiException(ErrorCode.USER_DUPLICATED);
        }

        AdminUser adminUser = AdminUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userNickname(request.getUserNickname())
                .build();

        AdminUser saved = adminUserRepository.save(adminUser);
        return new AdminAuthResponse(saved.getId());
    }

    @Override
    public AdminAuthResponse login(LoginRequest request) {
        AdminUser user = adminUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ApiException(ErrorCode.PASSWORD_NOT_CORRECT);
        }
        return new AdminAuthResponse(user.getId());
    }

}
