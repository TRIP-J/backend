package com.tripj.domain.country.repository;

import com.tripj.domain.country.model.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
