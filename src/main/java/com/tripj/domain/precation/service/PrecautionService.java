package com.tripj.domain.precation.service;

import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.precation.model.constant.PrecautionCate;
import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.domain.precation.model.entity.Precaution;
import com.tripj.domain.precation.repository.PrecautionRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.tripj.global.code.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PrecautionService {

    private final PrecautionRepository precautionRepository;
    private final CountryRepository countryRepository;

    /**
     * 나라별 주의사항 전체 조회
     */
    public GetPrecautionListResponse getPrecautionList(Long countryId, PrecautionCate precautionCate) {

        countryRepository.findById(countryId)
            .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_COUNTRY));

        String precaution;
        switch (precautionCate) {
            case CULTURE:
                precaution = precautionRepository.findCultureByCountryId(countryId)
                        .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_PRECAUTION));
                break;
            case TRAFFIC:
                precaution = precautionRepository.findTrafficByCountryId(countryId)
                        .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_PRECAUTION));
                break;
            case CONTACT:
                precaution = precautionRepository.findContactByCountryId(countryId)
                        .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_PRECAUTION));
                break;
            case ACCIDENT:
                precaution = precautionRepository.findAccidentByCountryId(countryId)
                        .orElseThrow(() -> new NotFoundException(E404_NOT_EXISTS_PRECAUTION));
                break;
            default:
                throw new NotFoundException(E404_NOT_EXISTS_PRECAUTION);
        }

        return GetPrecautionListResponse.of(precaution);
    }

}
