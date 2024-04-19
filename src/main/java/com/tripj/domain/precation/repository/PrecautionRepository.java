package com.tripj.domain.precation.repository;

import com.tripj.domain.precation.model.dto.GetPrecautionDetailResponse;
import com.tripj.domain.precation.model.dto.GetPrecautionListResponse;
import com.tripj.domain.precation.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PrecautionRepository extends JpaRepository<Precaution, Long>, PrecautionRepositoryCustom {

    List<GetPrecautionListResponse> findByCountryId(Long countryId);


}
