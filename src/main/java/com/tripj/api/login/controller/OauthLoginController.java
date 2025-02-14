package com.tripj.api.login.controller;

import com.tripj.api.login.dto.OauthLoginDto;
import com.tripj.api.login.service.OauthLoginService;
import com.tripj.api.login.validator.OauthValidator;
import com.tripj.domain.user.constant.UserType;
import com.tripj.global.util.AuthorizationHeaderUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/oauth")
@Tag(name = "authentication", description = "로그인/로그아웃/토큰재발급 API")
public class OauthLoginController {

    private final OauthValidator oauthValidator;
    private final OauthLoginService oauthLoginService;

    @Tag(name = "authentication")
    @Operation(summary = "소셜 로그인 API", description = "소셜 로그인 API")
    @PostMapping("/login")
    public ResponseEntity<OauthLoginDto.Response> oauthLogin(@RequestBody OauthLoginDto.Request oauthLoginRequestDto,
                                                             HttpServletRequest httpServletRequest) {

        String authorizationHeader = httpServletRequest.getHeader("Authorization");
        AuthorizationHeaderUtils.validateAuthorization(authorizationHeader);
        oauthValidator.validateUserType(oauthLoginRequestDto.getUserType());

        String accessToken = authorizationHeader.split(" ")[1]; //Bearer 다음에 오는 토큰

        OauthLoginDto.Response jwtTokenResponseDto =
                oauthLoginService.oauthLogin(accessToken, UserType.from(oauthLoginRequestDto.getUserType()));

        return ResponseEntity.ok(jwtTokenResponseDto);
    }
}
