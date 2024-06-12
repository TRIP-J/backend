package com.tripj.domain.board.model.dto.request;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class CreateBoardRequest {

    @Schema(description = "게시글 카테고리 Id", example = "1")
    private Long boardCateId;

    @Schema(description = "게시글 제목", example = "홍콩 맛집 추천")
    private String title;

    @Schema(description = "게시글 내용 Id", example = "게시글내용입니다.")
    private String content;

    //TODO : 첨부파일

    public Board toEntity(User user, BoardCate boardCate) {
        return Board.newBoard(title, content, user, boardCate);
    }
}
