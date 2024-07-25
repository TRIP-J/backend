package com.tripj.domain.board.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Schema(description = "게시글 상세 조회 DTO")
public class GetBoardDetailResponse {

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "유저 닉네임", example = "까만까마귀")
    private String userName;

    @Schema(description = "유저 프로필 사진",
            example = "http://t1.kakaocdn.net/account_images/default_profile.jpeg.twg.thumb.R110x110")
    private String profile;

    @Schema(description = "게시글 ID", example = "1")
    private Long boardId;

    @Schema(description = "게시글 카테고리명", example = "후기")
    private String boardCateName;

    @Schema(description = "게시글 제목", example = "오사카 여행 후기")
    private String title;

    @Schema(description = "게시글 내용", example = "너무 좋았어요")
    private String content;

    @Schema(description = "게시글 등록일", example = "2024-07-18 10:37:15")
    private LocalDateTime regTime;

    @Schema(description = "댓글 수", example = "5")
    private Long commentCnt;

    @Schema(description = "좋아요 수", example = "20")
    private Long likeCnt;

    @Schema(description = "이미지 url",
            example = "https://bucket.kr.object.ncloudstorage.com/image/thumbnail.png")
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
