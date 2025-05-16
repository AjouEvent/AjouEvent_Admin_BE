package com.ajouevent.admin.dto.request;

import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    private String email;
    private String password;
}
