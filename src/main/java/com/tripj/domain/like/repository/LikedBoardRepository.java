package com.tripj.domain.like.repository;

import com.tripj.domain.like.model.dto.CreateLikedBoardResponse;
import com.tripj.domain.like.model.entity.LikedBoard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikedBoardRepository extends JpaRepository<LikedBoard, Long> {

    Optional<LikedBoard> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

}
