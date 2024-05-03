package com.tripj.domain.user.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.global.util.DateTimeUtils;
import com.tripj.jwt.dto.JwtTokenDto;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "`user`")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Trip> trip = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Item> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private UserType userType;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(length = 200)
    private String password;

    @Column(nullable = false, length = 20)
    private String userName;

    private String nickname;

    @Column(length = 200)
    private String profile;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Role role;

    @Column(length = 250)
    private String refreshToken;

    private LocalDateTime tokenExpirationTime;

    @Builder
    public User(UserType userType, String email, String password, String nickname,
                  String userName, String profile, Role role) {
        this.userType = userType;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.userName = userName;
        this.profile = profile;
        this.role = role;
    }

    public void updateRefreshToken(JwtTokenDto jwtTokenDto) {
        refreshToken = jwtTokenDto.getRefreshToken();
        tokenExpirationTime = DateTimeUtils.convertToLocalDateTime(jwtTokenDto.getRefreshTokenExpireTime());
    }



}
