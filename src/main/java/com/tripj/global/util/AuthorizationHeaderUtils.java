package com.tripj.global.util;

import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.AuthenticationException;
import com.tripj.jwt.constant.GrantType;
import org.springframework.util.StringUtils;

public class AuthorizationHeaderUtils {

    public static void validateAuthorization(String authorizationHeader) {

        //1. authorizationHeader 필수 체크
        if (!StringUtils.hasText(authorizationHeader)) {
            throw new AuthenticationException(ErrorCode.NOT_EXISTS_AUTHORIZATION);
        }

        //2. authorizationHeader Bearer 체크
        String[] authorizations = authorizationHeader.split(" ");
        //authorizationHeader 예 : Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ6aGFuZ3NhbiIsImV4cCI6MTYyMjUwNjYwNiw
        if (authorizations.length < 2 || (!GrantType.BEARER.getType().equals(authorizations[0]))) {
            throw new AuthenticationException(ErrorCode.NOT_VALID_BEARER_GRANT_TYPE);
        }

    }


}
