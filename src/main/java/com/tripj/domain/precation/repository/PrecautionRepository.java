package com.tripj.domain.precation.repository;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.domain.precation.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrecautionRepository extends JpaRepository<Precaution, Long>, PrecautionRepositoryCustom {
    Optional<Precaution> findByCountry(Country country);

//    List<GetPrecautionListResponse> findByCountryId(Long countryId);


}
