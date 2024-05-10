package com.tripj.domain.precation.repository;

import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.domain.precation.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrecautionRepository extends JpaRepository<Precaution, Long>, PrecautionRepositoryCustom {

    List<GetPrecautionListResponse> findByCountryId(Long countryId);


}
