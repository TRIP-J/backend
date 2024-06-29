package com.tripj.domain.comment.model.dto.response;

import com.tripj.domain.comment.model.entity.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCommentResponse {

    @Schema(description = " 댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = " 게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "댓글 내용", example = "라면 같이 먹으러 가용")
    private String content;

    public static CreateCommentResponse of(Comment comment) {
        return new CreateCommentResponse(
                comment.getId(), comment.getBoard().getId(), comment.getContent());
    }
}
