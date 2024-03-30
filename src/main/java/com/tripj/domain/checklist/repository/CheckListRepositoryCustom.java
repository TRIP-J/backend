package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.dto.GetCheckListResponse;

import java.util.List;

public interface CheckListRepositoryCustom {

    List<GetCheckListResponse> getCheckList(Long itemCateId, Long userId);

}
