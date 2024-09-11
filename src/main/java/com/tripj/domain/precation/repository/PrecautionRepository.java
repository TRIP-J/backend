package com.tripj.domain.precation.repository;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.precation.model.entity.Precaution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrecautionRepository extends JpaRepository<Precaution, Long> {
    Optional<Precaution> findByCountry(Country country);

    @Query("select p.culture from Precaution p where p.country.id = :countryId")
    Optional<String> findCultureByCountryId(@Param("countryId") Long countryId);

    @Query("select p.traffic from Precaution p where p.country.id = :countryId")
    Optional<String> findTrafficByCountryId(@Param("countryId") Long countryId);

    @Query("select p.contact from Precaution p where p.country.id = :countryId")
    Optional<String> findContactByCountryId(@Param("countryId") Long countryId);

    @Query("select p.accident from Precaution p where p.country.id = :countryId")
    Optional<String> findAccidentByCountryId(@Param("countryId") Long countryId);

}
