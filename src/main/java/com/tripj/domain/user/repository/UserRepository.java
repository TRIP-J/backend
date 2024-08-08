package com.tripj.domain.user.repository;

import com.tripj.domain.user.model.dto.response.GetNicknameResponse;
import com.tripj.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findNicknameById(Long userId);

    Optional<User> findByRefreshToken(String refreshToken);
}
