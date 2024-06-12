package com.tripj.global.error.exception;

import com.tripj.global.code.ErrorCode;

public class InvalidException extends CustomRunTimeException {

    public InvalidException(ErrorCode errorCode) {
        super(errorCode.getMessage(), errorCode);
    }

}
