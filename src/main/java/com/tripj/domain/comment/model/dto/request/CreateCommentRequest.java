package com.tripj.domain.comment.model.dto.request;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.comment.model.entity.Comment;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateCommentRequest {

    @Schema(description = " 게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = " 게시글 내용",
            example = "라면 같이 드실래요?")
    private String content;

    public Comment toEntity(User user, Board board) {
        return Comment.newComment(user, board, content);
    }

}
