package com.tripj.domain.precation.repository;

import com.tripj.domain.precation.model.dto.response.GetPrecautionDetailResponse;

public interface PrecautionRepositoryCustom {

    GetPrecautionDetailResponse getPrecautionDetail(Long precautionId);
}
