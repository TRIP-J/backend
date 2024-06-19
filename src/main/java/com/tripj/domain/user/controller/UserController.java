package com.tripj.domain.user.controller;

import com.tripj.domain.user.model.dto.response.GetNicknameResponse;
import com.tripj.domain.user.service.UserService;
import com.tripj.global.model.RestApiResponse;
import com.tripj.resolver.userinfo.UserInfo;
import com.tripj.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
@Tag(name = "user", description = "사용자 API")
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "사용자 닉네임 조회 API",
            description = "사용자 닉네임을 조회합니다."
    )
    @GetMapping("")
    public RestApiResponse<GetNicknameResponse> getNickname(
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                userService.getNickname(userInfo.getUserId()));
    }

}
