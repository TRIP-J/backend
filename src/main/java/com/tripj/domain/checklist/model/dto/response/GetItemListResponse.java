package com.tripj.domain.checklist.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "체크리스트 > 아이템 일괄 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetItemListResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "아이템 고정 여부", example = "F")
    private String fix;

    @QueryProjection
    public GetItemListResponse(Long itemId, String itemName, Long itemCateId, String fix) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCateId = itemCateId;
        this.fix = fix;
    }


}
