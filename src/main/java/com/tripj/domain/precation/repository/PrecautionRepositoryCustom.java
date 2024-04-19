package com.tripj.domain.precation.repository;

import com.tripj.domain.precation.model.dto.GetPrecautionDetailResponse;

public interface PrecautionRepositoryCustom {

    GetPrecautionDetailResponse getPrecautionDetail(Long precautionId);
}
