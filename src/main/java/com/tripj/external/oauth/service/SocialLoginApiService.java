package com.tripj.external.oauth.service;


import com.tripj.external.oauth.model.OAuthAttributes;

public interface SocialLoginApiService {

    OAuthAttributes getUserInfo(String accessToken);
}
