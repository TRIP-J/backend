package com.tripj.domain.like.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateLikedBoardResponse {

    @Schema(description = "게시물 ID", example = "1")
    private Long boardId;

    @Schema(description = "좋아요 ID", example = "1")
    private Long likedBoardId;

    public static CreateLikedBoardResponse of(Long boardId, Long likedBoardId) {
        return new CreateLikedBoardResponse(boardId, likedBoardId);
    }


}
