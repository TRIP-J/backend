package com.tripj.domain.checklist.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "체크리스트 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCheckListResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 카테고리 명", example = "추천템")
    private String itemCateName;

    @QueryProjection
    public GetCheckListResponse(Long itemId, String itemName, String itemCateName) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCateName = itemCateName;
    }

}
