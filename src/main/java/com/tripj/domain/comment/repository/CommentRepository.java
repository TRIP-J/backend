package com.tripj.domain.comment.repository;

import com.tripj.domain.board.model.dto.response.GetBoardCommentResponse;
import com.tripj.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByBoardId(Long boardId);

    List<GetBoardCommentResponse> findByBoardId(Long boardId);

}
