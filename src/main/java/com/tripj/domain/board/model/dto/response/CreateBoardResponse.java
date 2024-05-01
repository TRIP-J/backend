package com.tripj.domain.board.model.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardResponse {

    private Long boardId;

    public static CreateBoardResponse of(Long boardId) {
        return new CreateBoardResponse(boardId);
    }

}
