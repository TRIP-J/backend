package com.tripj.global.error.exception;

import com.tripj.global.code.ErrorCode;

/**
 * 강제로 404 반환할때 사용.
 */
public class NotFoundException extends CustomRunTimeException {

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

}
