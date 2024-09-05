package com.tripj.api.login.controller;

import com.tripj.api.login.dto.RefreshTokenRequest;
import com.tripj.api.login.dto.TokenResponse;
import com.tripj.api.login.service.TokenService;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.model.RestApiResponse;
import com.tripj.global.util.AuthorizationHeaderUtils;
import com.tripj.jwt.service.TokenManager;
import io.jsonwebtoken.Claims;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class TokenController {

    private final TokenService tokenService;
    private final TokenManager tokenManager;
    private final UserRepository userRepository;


    @Tag(name = "authentication")
    @Operation(summary = "Access Token 재발급 API", description = "Header를 이용한 Access Token 재발급 API")
    @PostMapping("/access-token/issue")
    public RestApiResponse<TokenResponse> createAccessToken(HttpServletRequest httpServletRequest) {
        String authorizationHeader = httpServletRequest.getHeader("Authorization");

        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);

        String accessToken = authorizationHeader.split(" ")[1];

        Claims tokenClaims = tokenManager.getTokenClaims(accessToken);
        Long userId = Long.valueOf((Integer) tokenClaims.get("userId"));

        String refreshToken = userRepository.findRefreshTokenById(userId);

        TokenResponse tokenResponseDto
                = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return RestApiResponse.success(tokenResponseDto);
    }

    @Tag(name = "authentication")
    @Operation(summary = "Access Token 재발급 API 2", description = "ReqeustBody를 이용한 Access Token 재발급 API")
    @PostMapping("/access-token/issue2")
    public RestApiResponse<TokenResponse> createAccessToken(
            @RequestBody RefreshTokenRequest request) {

        String refreshToken = request.getRefreshToken();
        TokenResponse tokenResponseDto = tokenService.createAccessTokenByRefreshToken(refreshToken);

        return RestApiResponse.success(tokenResponseDto);
    }

}
