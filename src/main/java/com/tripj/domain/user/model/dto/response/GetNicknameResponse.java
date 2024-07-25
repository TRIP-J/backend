package com.tripj.domain.user.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNicknameResponse {

    @Schema(description = "유저 닉네임", example = "까만까마귀")
    private String nickname;

    public static GetNicknameResponse of(String nickname) {
        return new GetNicknameResponse(nickname);
    }
}
