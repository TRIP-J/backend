package com.tripj.external.oauth.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

//https://developers.kakao.com/docs/latest/ko/kakaologin/rest-api#profile
@Getter @Setter
public class KakaoUserInfoResponseDto {

    private String id;

    @JsonProperty("kakao_account")
    private KakaoAccount kakaoAccount;

    @Getter @Setter
    public static class KakaoAccount {
        private String email;
        private Profile profile;

        @Getter @Setter
        public static class Profile {
            private String nickname;

            @JsonProperty("thumbnail_image_url")
            private String thumbnailImageUrl;
        }


    }



}
