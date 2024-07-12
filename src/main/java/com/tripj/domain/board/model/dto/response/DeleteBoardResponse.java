package com.tripj.domain.board.model.dto.response;

import com.tripj.domain.board.model.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteBoardResponse {

    @Schema(description = "게시글 Id", example = "1")
    private Long boardId;

    public static DeleteBoardResponse of(Board board) {
        return new DeleteBoardResponse(board.getId());
    }

}
