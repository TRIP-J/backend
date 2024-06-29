package com.tripj.domain.board.model.dto.response;

import com.tripj.domain.board.model.entity.Board;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateBoardResponse {

    @Schema(description = "게시글 Id", example = "1")
    private Long boardId;

    @Schema(description = "게시글 카테고리 Id", example = "1")
    private Long boardCateId;

    @Schema(description = "게시글 제목", example = "홍콩 맛집 추천")
    private String title;

    @Schema(description = "게시글 내용 Id", example = "게시글내용입니다.")
    private String content;

    public static CreateBoardResponse of(Board board) {
        return new CreateBoardResponse(board.getId(), board.getBoardCate().getId(), board.getTitle(), board.getContent());
    }

}
