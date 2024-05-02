package com.tripj.domain.board.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardCommentResponse {

    private Long userId;
    private Long boardId;
    private Long commentId;
    private String content;

    public static GetBoardCommentResponse of(Long userId, Long boardId,
                                             Long commentId, String content) {
        return new GetBoardCommentResponse(userId, boardId, commentId, content);
    }


}
