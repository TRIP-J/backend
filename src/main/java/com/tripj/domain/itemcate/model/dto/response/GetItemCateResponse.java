package com.tripj.domain.itemcate.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetItemCateResponse {

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "아이템 카테고리 명", example = "의류")
    private String itemCateName;


    public static GetItemCateResponse of(Long itemCateId, String itemCateName) {
        return new GetItemCateResponse(itemCateId, itemCateName);
    }
}
