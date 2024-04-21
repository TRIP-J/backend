package com.tripj.domain.board.model.dto;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.user.model.entity.User;
import lombok.Getter;

@Getter
public class CreateBoardRequest {

    private Long boardCateId;
    private String title;
    private String content;
    //TODO : 첨부파일

    public Board toEntity(User user, BoardCate boardCate) {
        return Board.newBoard(title, content,
                              user, boardCate);
    }
}
