package com.tripj.domain.board.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class GetBoardDetailResponse {

    private Long userId;
    private String userName;
    private String profile;

    private Long boardId;
    private String boardCateName;
    private String title;
    private String content;
    private LocalDateTime regTime;

    private Long commentCnt;
    private Long likeCnt;

    private List<String> imgUrl;

    @QueryProjection
    public GetBoardDetailResponse(Long userId, String userName, String profile,
                                  Long boardId, String boardCateName,
                                  String title, String content, LocalDateTime regTime,
                                  Long commentCnt, Long likeCnt) {
        this.userId = userId;
        this.userName = userName;
        this.profile = profile;
        this.boardId = boardId;
        this.boardCateName = boardCateName;
        this.title = title;
        this.content = content;
        this.regTime = regTime;
        this.commentCnt = commentCnt;
        this.likeCnt = likeCnt;
    }
}
