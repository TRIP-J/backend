package com.tripj.domain.item.model.dto.response;

import com.tripj.domain.item.model.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateItemResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "아이템명", example = "고데기")
    private String itemName;

    public static UpdateItemResponse of(Item item) {
        return new UpdateItemResponse(item.getId(), item.getItemName());
    }
}
