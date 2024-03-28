package com.tripj.web.kakaotoken.util;

import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class KakaoApiUtil {

	/**
	 * Kakao <-> 개발 Application 인증 요청 URL
	 * */
	public static String getKakaoAuthorizeURL(String clientId, String redirectUri) {
//		String baseURL 		= KakaoAPI.getRequestURL(KakaoAPI.KAKAO_AUTHORIZE);
//		baseURL = Scheme.HTTPS.name() + "://" + baseURL;

		UriComponents uriComponents = UriComponentsBuilder
			.fromUri(URI.create("https://kauth.kakao.com/oauth/authorize"))
			.queryParam("response_type"	, "code")
			.queryParam("client_id"		, clientId)
			.queryParam("redirect_uri"	, redirectUri)
			.build();

		return uriComponents.toUriString();
	}


}
