package com.tripj.domain.user.repository;

import com.tripj.domain.user.model.dto.response.GetNicknameResponse;
import com.tripj.domain.user.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

    Optional<User> findByEmail(String email);

    boolean existsByNickname(String nickname);

    Optional<User> findNicknameAndProfileById(Long userId);

    Optional<User> findByRefreshToken(String refreshToken);

    @Query("SELECT u.refreshToken FROM User u WHERE u.id = :userId")
    String findRefreshTokenById(@Param("userId") Long userId);

//    @Modifying
//    @Query("UPDATE User u SET u.tokenExpirationTime = :now WHERE u.id = :userId")
//    User logoutByRefreshToken(@Param("userId") Long userId, @Param("now") LocalDateTime now);

}
