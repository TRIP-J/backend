package com.tripj.domain.board.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
public class GetBoardResponse {

    private Long userId;
    private String userName;
    private String profile;

    private Long boardId;
    private Long boardCateId;
    private String boardCateName;
    private String title;
    private String content;
    private LocalDateTime regTime;

    private Long commentCnt;
    private Long likeCnt;

    public static GetBoardResponse of(
            Long userId, String userName, String profile,
            Long boardId, Long boardCateId, String title,
            String content, String boardCateName, LocalDateTime regTime,
            Long commentCnt, Long likeCnt) {

        return new GetBoardResponse(userId, userName, profile,
                                    boardId, boardCateId, title, content,
                                    boardCateName, regTime,
                                    commentCnt, likeCnt);
    }

    @QueryProjection
    public GetBoardResponse(Long userId, String userName, String profile,
                            Long boardId, Long boardCateId, String boardCateName,
                            String title, String content, LocalDateTime regTime,
                            Long commentCnt, Long likeCnt) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.boardId = boardId;
        this.boardCateId = boardCateId;
        this.boardCateName = boardCateName;
        this.title = title;
        this.content = content;
        this.regTime = regTime;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
    }
}
