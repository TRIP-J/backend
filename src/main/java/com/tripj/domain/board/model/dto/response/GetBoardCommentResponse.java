package com.tripj.domain.board.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "게시글 상세 댓글 조회 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardCommentResponse {

    @Schema(description = "댓글 ID", example = "1")
    private Long id;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "댓글 내용", example = "같이 여행 하실래요?")
    private String content;

    public static GetBoardCommentResponse of(Long userId, Long boardId,
                                             Long commentId, String content) {
        return new GetBoardCommentResponse(userId, boardId, commentId, content);
    }


}
