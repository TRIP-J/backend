package com.tripj.domain.precation.repository;

import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.domain.precation.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrecautionRepository extends JpaRepository<Precaution, Long>, PrecautionRepositoryCustom {

    List<GetPrecautionListResponse> findByCountryId(Long countryId);


}
