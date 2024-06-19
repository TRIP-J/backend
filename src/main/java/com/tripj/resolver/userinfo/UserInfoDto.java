package com.tripj.resolver.userinfo;

import com.tripj.domain.user.constant.Role;
import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class UserInfoDto {

    private Long userId;
    private Role role;

}
