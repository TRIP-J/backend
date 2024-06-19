package com.tripj.domain.like.repository;

import com.tripj.domain.like.model.entity.LikedBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikedBoardRepository extends JpaRepository<LikedBoard, Long> {

    Optional<LikedBoard> findByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByUserIdAndBoardId(Long userId, Long boardId);

    void deleteByBoardId(Long boardId);
}
