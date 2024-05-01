package com.tripj.domain.board.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class GetBoardResponse {

    private Long userId;
    private String userName;
    private String profile;

    private Long boardId;
    private String title;
    private String content;

    public static GetBoardResponse of(
            Long userId, String userName, String profile,
            Long boardId, String title, String content) {

        return new GetBoardResponse(userId, userName, profile,
                                    boardId, title, content);
    }

    @QueryProjection
    public GetBoardResponse(Long userId, String userName, String profile,
                            Long boardId, String title, String content) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.boardId = boardId;
        this.title = title;
        this.content = content;
    }
}
