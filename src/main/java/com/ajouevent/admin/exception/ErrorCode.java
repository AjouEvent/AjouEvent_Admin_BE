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
    ALREADY_BLACKLISTED(400301, "이미 블랙리스트에 등록된 회원입니다."),
    BLACKLIST_ENTRY_NOT_FOUND(404302, "블랙리스트에 등록되지 않은 회원입니다."),
    INQUIRY_NOT_FOUND(404601, "존재하지 않는 문의입니다."),
    INVALID_SUBJECT(400003, "patchNote만 등록할 수 있습니다."),
    CLUB_EVENT_NOT_FOUND(404003, "이벤트를 찾을 수 없습니다."),
    FILE_UPLOAD_FAILED(500001, "파일 업로드에 실패했습니다.");
    private final int code;
    private final String message;
}