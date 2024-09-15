package com.tripj.domain.item.model.dto.request;

import com.tripj.domain.item.constant.SetItemCate;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateSetItemRequest {

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "여행 Id", example = "1")
    private Long tripId;

    @Schema(description = "세트 아이템 카테고리", example = "BASIC")
    private SetItemCate setItemCate;

    public Item toEntity(User user, ItemCate itemCate, Trip trip, String itemName) {
        return Item.newItem(
                itemName,
                user,
                itemCate,
                trip);
    }

}