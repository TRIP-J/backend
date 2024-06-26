package com.tripj.domain.user.service;

import com.tripj.domain.user.model.dto.response.GetNicknameResponse;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.nickname.GenerateRandomNicknameRepository;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.BusinessException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final GenerateRandomNicknameRepository nicknameRepository;

    /**
     * 회원가입
     */
    public User registerUser(User user) {
        validateDuplicateUser(user);
        return userRepository.save(user);
    }

    /**
     * 회원 중복 검사
     */
    private void validateDuplicateUser(User user) {
        Optional<User> optionalUser = userRepository.findByEmail(user.getEmail());

        if (optionalUser.isPresent()) {
            throw new BusinessException(ErrorCode.ALREADY_REGISTERED_USER);
        }
    }

    /**
     * 이메일로 회원 찾기
     */
    @Transactional(readOnly = true)
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /**
     * 랜덤 닉네임 생성
     */
    public String generateRandomNickname() {
        while (true) {
            String nickname = nicknameRepository.generate();
            if (!userRepository.existsByNickname(nickname)) {
                return nickname;
            }
        }
    }

    /**
     * 사용자 닉네임 조회
     */
    @Transactional(readOnly = true)
    public GetNicknameResponse getNickname(Long userId) {

        User user = userRepository.findNicknameById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        return GetNicknameResponse.of(user.getNickname());
    }
}
