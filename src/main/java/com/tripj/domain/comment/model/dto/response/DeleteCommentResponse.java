package com.tripj.domain.comment.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteCommentResponse {

    @Schema(description = " 댓글 ID", example = "1")
    private Long commentId;

    @Schema(description = " 게시글 ID", example = "1")
    private Long boardId;

    public static DeleteCommentResponse of(Long commentId, Long boardId) {
        return new DeleteCommentResponse(commentId, boardId);
    }
}
