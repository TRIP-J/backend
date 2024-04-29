package com.tripj.domain.comment.repository;

import com.tripj.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByBoardId(Long boardId);
}
