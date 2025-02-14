package com.tripj.domain.item.model.dto.response;

import com.tripj.domain.item.model.entity.Item;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateItemResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "아이템명", example = "고데기")
    private String itemName;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "여행 Id", example = "1")
    private Long tripId;

    public static CreateItemResponse of(Item item) {
        return new CreateItemResponse(
                item.getId(), item.getItemName(),
                item.getItemCate().getId(),
                item.getTrip().getId());
    }
}
