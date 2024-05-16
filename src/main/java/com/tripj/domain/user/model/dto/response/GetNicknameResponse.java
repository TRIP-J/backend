package com.tripj.domain.user.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetNicknameResponse {

    private String nickname;

    public static GetNicknameResponse of(String nickname) {
        return new GetNicknameResponse(nickname);
    }
}
