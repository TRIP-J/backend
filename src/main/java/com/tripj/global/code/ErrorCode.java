package com.tripj.global.code;

import lombok.Getter;

import static com.tripj.global.code.HttpStatusCode.*;

@Getter
public enum ErrorCode {

    TEST(INTERNAL_SERVER_ERROR, false, "T001", "business exception test"),

    /**
     * 회원
     */
    INVALID_USER_TYPE(BAD_REQUEST, false, "M001", "잘못된 회원 타입 입니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST, false, "M002", "이미 가입된 회원입니다."),

    /**
     * 인증 && 인가
     */
    TOKEN_EXPIRED(UNAUTHORIZED, false, "A001", "토큰이 만료되었습니다."),
    NOT_VALID_TOKEN(UNAUTHORIZED, false, "A002", "해당 토큰은 유효한 토큰이 아닙니다."),
    NOT_EXISTS_AUTHORIZATION(UNAUTHORIZED,false ,"A003" ,"Authorization Header가 빈값입니다." ),
    NOT_VALID_BEARER_GRANT_TYPE(UNAUTHORIZED,false ,"A004", "인증 타입이 Bearer 타입이 아닙니다." ),
    REFRESH_TOKEN_NOT_FOUND(UNAUTHORIZED, false, "A005", "해당 refresh token은 존재하지 않습니다."),
    REFRESH_TOKEN_EXPIRED(UNAUTHORIZED, false, "A006", "해당 refresh token은 만료되었습니다."),
    NOT_ACCESS_TOKEN_TYPE(UNAUTHORIZED, false, "A007", "해당 토큰은 ACCESS TOKEN이 아닙니다."),
    FORBIDDEN_ADMIN(FORBIDDEN, false, "A-008", "관리자 Role이 아닙니다.");


    private final HttpStatusCode statusCode;
    private final boolean notification;
    private final String code;
    private final String message;

    ErrorCode(HttpStatusCode statusCode, boolean notification, String code, String message) {
        this.statusCode = statusCode;
        this.notification = notification;
        this.code = code;
        this.message = message;
    }
}
