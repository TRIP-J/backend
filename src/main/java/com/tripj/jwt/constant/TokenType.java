package com.tripj.jwt.constant;

public enum TokenType {

    ACCESS, REFRESH;

    public static boolean isAccessToken(String tokenType) { //tokenType이 ACCESS면 TRUE
        return TokenType.ACCESS.name().equals(tokenType);
    }
}
