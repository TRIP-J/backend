package com.tripj.domain.item.repository;

import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;
import com.tripj.domain.item.constant.ItemType;

import java.util.List;

public interface ItemRepositoryCustom {

    List<GetItemListResponse> getItemList(Long userId);
    GetItemListResponse getItem(Long userId, Long itemId, ItemType itemType);

}
