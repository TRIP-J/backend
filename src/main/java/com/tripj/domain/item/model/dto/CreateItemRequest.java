package com.tripj.domain.item.model.dto;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateItemRequest {

    @Schema(description = "아이템명", example = "고데기")
    @NotNull(message = "아이템명은 필수로 입력해 주세요.")
    private String itemName;

    @Schema(description = "이전 여행 기록 분기 컬럼", example = "NOW")
    private String previous;

    @Schema(description = "나라 Id", example = "1")
    private Long countryId;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;

    @Schema(description = "여행 Id", example = "1")
    private Long tripId;

    public Item toEntity(User user, ItemCate itemCate, Country country, Trip trip) {
        return Item.newItem(
                itemName,
                previous,
                user,
                itemCate,
                country,
                trip);
    }

}
