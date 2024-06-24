package com.tripj.domain.item.model.dto.request;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateItemRequest {

    @Schema(description = "아이템명", example = "고데기")
    @NotNull(message = "아이템명은 필수로 입력해 주세요.")
    private String itemName;

}
