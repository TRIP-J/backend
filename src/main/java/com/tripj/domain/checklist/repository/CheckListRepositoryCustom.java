package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetMyCheckListResponse;

import java.util.List;

public interface CheckListRepositoryCustom {

    List<GetCheckListResponse> getCheckList(Long userId, Long tripId);
}
