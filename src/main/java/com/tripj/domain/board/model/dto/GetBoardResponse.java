package com.tripj.domain.board.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardResponse {

    private Long boardId;
    private String title;
    private String content;

    public static GetBoardResponse of(
            Long boardId, String title, String content) {
        return new GetBoardResponse(boardId, title, content);
    }

}
