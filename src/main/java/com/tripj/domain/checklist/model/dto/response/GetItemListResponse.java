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

    @QueryProjection
    public GetItemListResponse(Long itemId, String itemName) {
        this.itemId = itemId;
        this.itemName = itemName;
    }


}
