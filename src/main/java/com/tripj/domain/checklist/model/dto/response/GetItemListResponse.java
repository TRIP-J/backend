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
@Schema(description = "체크리스트 > 아이템 일괄 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetItemListResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "아이템 타입", example = "FIXED")
    private ItemType itemType;

    @Schema(description = "체크리스트에 추가 가능 여부 \n" +
            "ALREADY = 체크리스트에 추가된 아이템 \n" +
            "NOT_YET = 아직 추가 안된 아이템",
            example = "ALREADY")
    private String addStatus;



    @QueryProjection
    public GetItemListResponse(Long itemId, String itemName, Long itemCateId, ItemType itemType) {
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemCateId = itemCateId;
        this.itemType = itemType;
    }


}
