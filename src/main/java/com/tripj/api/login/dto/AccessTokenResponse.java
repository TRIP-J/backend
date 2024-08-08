package com.tripj.api.login.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter @Builder
public class AccessTokenResponse {

    @Schema(description = "grantType", example = "Bearer", required = true)
    private String grantType;

    @Schema(description = "accessToken", example = "eyJ0eXAiOiJKV1QiLCJhbGDiOJqIUzUxMiJ9.Q", required = true)
    private String accessToken;

    @Schema(description = "access token 만료 시간", example = "1997-01-01 00:57:50", required = true)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private Date accessTokenExpireTime;

}
