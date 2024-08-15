package com.tripj.global.interceptor;

import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.AuthenticationException;
import com.tripj.global.util.AuthorizationHeaderUtils;
import com.tripj.jwt.constant.TokenType;
import com.tripj.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final TokenManager tokenManager;

    //preHandle() 메서드는 컨트롤러보다 먼저 수행
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();


        // 1. Authorization Header 검증
        String authorizationHeader = request.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        // 0. refreshToken으로 accessToken 재발급시에는 검증 생략
        if (requestURI.equals("/api/access-token/issue2")) {
            return true;
        }

        // 2. 토큰 검증
        String accessToken = authorizationHeader.split(" ")[1];
        tokenManager.validateToken(accessToken);

        // 3. 토큰 타입
        Claims tokenClaims = tokenManager.getTokenClaims(accessToken); // 토큰의 내용을 조회
        String tokenType = tokenClaims.getSubject(); // 토큰의 타입을 조회

        if (!TokenType.isAccessToken(tokenType)) {
            throw new AuthenticationException(ErrorCode.NOT_ACCESS_TOKEN_TYPE);
        }

        return true;
    }
}
