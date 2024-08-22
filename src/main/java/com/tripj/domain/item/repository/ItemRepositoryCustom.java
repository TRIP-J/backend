package com.tripj.domain.item.repository;

import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;

import java.util.List;

public interface ItemRepositoryCustom {

    List<GetItemListResponse> getItemList(Long userId);

}
