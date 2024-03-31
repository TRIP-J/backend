package com.tripj.domain.checklist.model.dto;

import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.item.model.entity.Item;
import lombok.Getter;

@Getter
public class CreateCheckListRequest {

    private Long itemId;
    private String pack;

    public CheckList toEntity(Item item) {
        return CheckList.newCheckList(item, pack);
    }
}
