package com.tripj.domain.checklist.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import com.tripj.domain.item.constant.ItemType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "체크리스트에 담은 아이템 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCheckListResponse {

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checkListId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 Id (B)", example = "1")
    private Long itemId;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "아이템 챙김 여부", example = "NO")
    private String pack;

    @Schema(description = "아이템 타입 (B)", example = "FIXED")
    private ItemType itemType;

    @QueryProjection
    public GetCheckListResponse(Long checkListId, String itemName, Long itemId, Long itemCateId, String pack, ItemType itemType) {
        this.checkListId = checkListId;
        this.itemName = itemName;
        this.itemId = itemId;
        this.itemCateId = itemCateId;
        this.pack = pack;
        this.itemType = itemType;
    }

}
