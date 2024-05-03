package com.tripj.api.login.service;

import com.tripj.api.login.dto.OauthLoginDto;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.service.UserService;
import com.tripj.external.oauth.kakao.service.SocialLoginApiServiceFactory;
import com.tripj.external.oauth.model.OAuthAttributes;
import com.tripj.external.oauth.service.SocialLoginApiService;
import com.tripj.jwt.dto.JwtTokenDto;
import com.tripj.jwt.service.TokenManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OauthLoginService {

    private final UserService userService;
    private final TokenManager tokenManager;

    public OauthLoginDto.Response oauthLogin(String accessToken, UserType userType) {
        SocialLoginApiService socialLoginApiService = SocialLoginApiServiceFactory.getSocialLoginApiService(userType); //어떤 social인지
        OAuthAttributes userInfo = socialLoginApiService.getUserInfo(accessToken);
        log.info("userInfo : {}", userInfo);

        JwtTokenDto jwtTokenDto;

        Optional<User> optionalUser = userService.findUserByEmail(userInfo.getEmail());
        //신규 회원가입
        if (optionalUser.isEmpty()) {
            String nickname = userService.generateRandomNickname();
            User oauthUser = userInfo.toUserEntity(userType, Role.ROLE_USER, nickname);
            oauthUser = userService.registerUser(oauthUser);

            //토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateRefreshToken(jwtTokenDto);
        } else { //기존 회원일 경우
            User oauthUser = optionalUser.get();

            //토큰 생성
            jwtTokenDto = tokenManager.createJwtTokenDto(oauthUser.getId(), oauthUser.getRole());
            oauthUser.updateRefreshToken(jwtTokenDto);
        }

        /**
         * 반환 타입이 OauthLoginDto.Response이므로 of메소드를 통해 변환
         * JwtTokenDto -> OauthLoginDto.Response로 변환하는 이유는
         * 결국 데이터를 클라이언트가 사용하기 편리한 형태로 가공하기 위함
         */
        return OauthLoginDto.Response.of(jwtTokenDto);
    }





}
