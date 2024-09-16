package com.tripj.domain.item.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class CreateItemCatePairResponse {

    private final String itemName;
    private final Long itemCateId;

}
