package com.tripj.domain.comment.repository;

import com.tripj.domain.board.model.dto.response.GetBoardCommentResponse;
import com.tripj.domain.comment.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    void deleteByBoardId(Long boardId);

    List<GetBoardCommentResponse> findByBoardId(Long boardId);

}
