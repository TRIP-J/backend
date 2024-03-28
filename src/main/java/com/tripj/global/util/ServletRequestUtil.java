package com.tripj.global.util;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

/**
 * Request 객체를 이용한 유틸리티 클래스
 * */
@Slf4j
public class ServletRequestUtil {

	public static String getServiceURL(HttpServletRequest request){
		String scheme 		= request.getScheme();
		String serverName 	= request.getServerName();
		String contextPath 	= request.getContextPath();
		int serverPort 		= request.getServerPort();

		String url = scheme + "://" + serverName;

		if(serverPort != 80 && serverPort != 443){
			url += ":" + serverPort;
		}

		url += contextPath;

		return url;
	}

	public static boolean isSafeURL(@NonNull String url){
		boolean isStartScheme = url.startsWith("http://") || url.startsWith("https://");
		// http 분할 응답 대비 (CR이나 LF가 들어가면 안됨)
		boolean isSafe = !url.contains("\n") && !url.contains("\r");
		return isStartScheme && isSafe;
	}

}
