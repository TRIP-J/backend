package com.tripj.domain.checklist.model.dto;

import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.item.model.entity.Item;
import com.tripj.domain.user.model.entity.User;
import lombok.Getter;

@Getter
public class CreateCheckListRequest {

    private Long itemId;
    private String pack;

    public CheckList toEntity(Item item, User user) {
        return CheckList.newCheckList(item, user, pack);
    }
}
