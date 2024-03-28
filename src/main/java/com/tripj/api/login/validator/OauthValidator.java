package com.tripj.api.login.validator;

import com.tripj.domain.user.constant.UserType;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class OauthValidator {

    // 갖고있는 userType 검증
    public void validateUserType(String userType) {
        if (!UserType.isUserType(userType)) {
            throw new BusinessException(ErrorCode.INVALID_USER_TYPE);
        }
    }

}
