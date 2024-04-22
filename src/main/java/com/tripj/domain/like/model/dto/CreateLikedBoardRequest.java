package com.tripj.domain.like.model.dto;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.like.model.entity.LikedBoard;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateLikedBoardRequest {

    @Schema(description = "게시글 Id", example = "1")
    private Long boardId;

    public LikedBoard toEntity(User user, Board board) {
        return LikedBoard.newLikedBoard(user, board);
    }

}
