package com.tripj.api.login.service;

import com.tripj.api.login.dto.AccessTokenResponse;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.service.UserService;
import com.tripj.jwt.constant.GrantType;
import com.tripj.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Transactional
@RequiredArgsConstructor
public class TokenService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public AccessTokenResponse createAccessTokenByRefreshToken(String refreshToken) {
        User user = userService.findUserByRefreshToken(refreshToken);

        Date accessTokenExpirationTime = tokenManager.createAccessTokenExpireTime();
        String accessToken = tokenManager.createAccessToken(user.getId(), user.getRole(), accessTokenExpirationTime);

        return AccessTokenResponse.builder()
                .grantType(GrantType.BEARER.getType())
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpirationTime)
                .build();
    }




}
