package com.tripj.domain.item.model.dto.request;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateItemRequest {

    @Schema(description = "아이템명", example = "고데기")
    @NotNull(message = "아이템명은 필수로 입력해 주세요.")
    private String itemName;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "여행 Id", example = "1")
    private Long tripId;

    @Schema(description = "아이템 타입", example = "USER_ADDED")
    private ItemType itemType;

    public Item toEntity(User user, ItemCate itemCate, Trip trip) {
        return Item.newItem(
                itemName,
                user,
                itemCate,
                trip);
    }

}
