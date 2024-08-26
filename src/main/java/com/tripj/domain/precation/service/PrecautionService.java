package com.tripj.domain.precation.service;

import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.precation.model.dto.response.GetPrecautionDetailResponse;
import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrecautionService {

//    private final PrecautionRepository precautionRepository;
    private final CountryRepository countryRepository;

    /**
     * 나라별 주의사항 전체 조회
     */
//    public List<GetPrecautionListResponse> getPrecautionList(Long countryId) {
//
//        countryRepository.findById(countryId)
//            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COUNTRY));
//
//        return precautionRepository.findByCountryId(countryId);
//    }

    /**
     * 주의사항 상세 조회
     */
//    public GetPrecautionDetailResponse getPrecautionDetail(Long precautionId) {
//
//        precautionRepository.findById(precautionId)
//            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_PRECAUTION));
//
//        return precautionRepository.getPrecautionDetail(precautionId);
//    }
}
