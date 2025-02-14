package com.tripj.global.code;

import lombok.Getter;

import static com.tripj.global.code.HttpStatusCode.*;

@Getter
public enum ErrorCode {

    TEST(INTERNAL_SERVER_ERROR, false, "T001", "business exception test"),

    /**
     * 400 Bad Request (잘못된 요청)
     */
    E400_BINDING_RESULT(BAD_REQUEST, false, "BR001", "잘못된 Binding 입니다"),
    E400_INVALID_UPLOAD_FILE_EXTENSION(BAD_REQUEST, false , "BR002", "잘못된 파일 확장자입니다."),
    E400_INVALID_FILE_COUNT_TOO_MANY(BAD_REQUEST, false , "BR003", "업로드 가능한 파일 개수를 초과했습니다."),

    /**
     * 403 Forbidden (권한 등의 이유로 허용하지 않는 요청)
     */
    E403_FORBIDDEN(FORBIDDEN, false, "FB000", "허용하지 않는 요청입니다"),
    E403_NOT_MY_ITEM(FORBIDDEN, false, "FB001", "자신의 아이템만 수정, 삭제 가능합니다."),
    E403_NOT_MY_CHECKLIST(FORBIDDEN, false, "FB002", "자신의 체크리스트만 수정, 삭제 가능합니다."),
    E403_NOT_MY_BOARD(FORBIDDEN, false, "FB003", "자신의 게시물만 수정, 삭제 가능합니다."),
    E403_NOT_MY_COMMENT(FORBIDDEN, false, "FB004", "자신의 댓글만 수정, 삭제 가능합니다."),
    E403_NOT_MY_NICKNAME(FORBIDDEN, false, "FB005", "자신의 닉네임만 수정 가능합니다."),
    E403_REFRESH_TOKEN_EXPIRED(FORBIDDEN, false, "FB006", "해당 refresh token은 만료되었습니다."),
    E403_NOT_FOUND_REFRESH_TOKEN(FORBIDDEN, false, "FB007", "해당 refresh token은 존재하지 않습니다."),

    /**
     * 404 Not Found
     */
    E404_NOT_EXISTS_USER(NOT_FOUND, false, "NF001", "존재하지 않는 회원입니다."),
    E404_NOT_EXISTS_COUNTRY(NOT_FOUND, false, "NF002", "존재하지 않는 나라입니다."),
    E404_NOT_EXISTS_ITEM_CATE(NOT_FOUND, false, "NF003", "존재하지 않는 아이템 카테고리입니다."),
    E404_NOT_EXISTS_ITEM(NOT_FOUND, false, "NF004", "존재하지 않는 아이템입니다."),
    E404_NOT_EXISTS_CHECKLIST(NOT_FOUND, false, "NF005", "존재하지 않는 체크리스트입니다."),
    E404_NOT_EXISTS_TRIP(NOT_FOUND, false, "NF006", "존재하지 않는 여행계획입니다."),
    E404_NOT_EXISTS_PRECAUTION(NOT_FOUND, false, "NF007", "존재하지 않는 주의사항입니다."),
    E404_NOT_EXISTS_BOARD(NOT_FOUND, false, "NF008", "존재하지 않는 게시글입니다."),
    E404_NOT_EXISTS_BOARD_CATE(NOT_FOUND, false, "NF009", "존재하지 않는 게시글 카테고리입니다."),
    E404_NOT_EXISTS_COMMENT(NOT_FOUND, false, "NF010", "존재하지 않는 게시글 댓글입니다."),
    E404_NOT_EXISTS_NOW_TRIP(NOT_FOUND, false, "NF011", "존재하지 않는 현재 여행계획입니다."),
    E404_NOT_EXISTS_NOW_ITEM(NOT_FOUND, false, "NF012", "존재하지 않는 현재 아이템입니다."),
    E404_NOT_EXISTS_FIXED_ITEM(NOT_FOUND, false, "NF013", "고정되어 있는 아이템이 아닙니다."),
    E404_NOT_EXISTS_CONVERSATION(NOT_FOUND, false, "NF014", "해당나라의 회화가 존재하지 않습니다."),
    E404_NOT_EXISTS_PAST_TRIP(NOT_FOUND, false, "NF0015", "존재하지 않는 지난 여행계획입니다."),
    E404_NOT_EXISTS_PRECAUTION_CATE(NOT_FOUND, false, "NF0016", "존재하지 않는 주의사항 카테고리입니다."),

    /**
     * 회원
     */
    INVALID_USER_TYPE(BAD_REQUEST, false, "M001", "잘못된 회원 타입입니다."),
    ALREADY_REGISTERED_USER(BAD_REQUEST, false, "M002", "이미 가입된 회원입니다."),
    ALREADY_EXISTED_NICKNAME(BAD_REQUEST, false, "M003", "이미 존재하는 닉네임입니다."),

    /**
     * 여행
     */
    ALREADY_EXISTS_TRIP(BAD_REQUEST, false, "C001", "이미 생성한 여행이 있습니다."),
    NOT_MY_TRIP(BAD_REQUEST, false, "C002", "자신이 생성한 여행이 아닙니다."),

    /**
     * 체크리스트
     */
    ALREADY_EXISTS_CHECKLIST(BAD_REQUEST, false, "C001", "이미 체크리스트에 추가된 아이템입니다."),
    NOT_MY_CHECKLIST(BAD_REQUEST, false, "C002", "자신이 생성한 체크리스트가 아닙니다."),

    /**
     * 아이템
     */
    NOT_ALLOWED_FIX_ITEM(BAD_REQUEST, false, "I001", "고정 아이템은 수정, 삭제 불가능합니다."),
    NOT_ALLOWED_PAST_ITEM(BAD_REQUEST, false, "I002", "지난 여행 계획의 아이템은 수정, 삭제 불가능합니다."),

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
    FORBIDDEN_ADMIN(FORBIDDEN, false, "A008", "관리자 Role이 아닙니다.");



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
