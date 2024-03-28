package com.tripj.global.error.exception;


import com.tripj.global.code.ErrorCode;

public class AuthenticationException extends BusinessException {

    public AuthenticationException(ErrorCode errorCode) {
        super(errorCode);
    }


}
