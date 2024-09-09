package com.tripj.domain.user.model.dto.response;

import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNicknameResponse {

    @Schema(description = "유저 닉네임", example = "까만까마귀")
    private String nickname;

    @Schema(description = "프로필 사진", example = "http://t1.kakaocdn.net/account_images/default_profile.R110x110")
    private String profile;

    public static GetNicknameResponse of(User user) {
        return new GetNicknameResponse(user.getNickname(), user.getProfile());
    }
}
