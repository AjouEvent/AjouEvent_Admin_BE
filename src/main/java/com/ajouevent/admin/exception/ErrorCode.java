package com.ajouevent.admin.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    UNAUTHORIZED(401001, "로그인이 필요한 요청입니다."), // -> 필터에서 던지는 에러 Json
    USER_NOT_FOUND(404001, "존재하지 않는 관리자 계정입니다."),
    MEMBER_NOT_FOUND(404002, "존재하지 않는 회원입니다."),
    USER_DUPLICATED(400001, "이미 사용 중인 이메일입니다."),
    PASSWORD_NOT_CORRECT(400002, "비밀번호가 일치하지 않습니다."),
    PERMISSION_ALREADY_GRANTED(400201, "이미 부여된 권한입니다."),
    PERMISSION_NOT_FOUND(404202, "해당 권한은 존재하지 않거나 부여되지 않았습니다.");

    private final int code;
    private final String message;
}