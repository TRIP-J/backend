package com.tripj.domain.country.service;

import com.tripj.domain.country.model.dto.GetCountryResponse;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.country.repository.CountryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CountryService {

    private final CountryRepository countryRepository;

    public List<GetCountryResponse> getCountry() {
        List<Country> countryList = countryRepository.findAll();
        List<GetCountryResponse> responseList = countryList.stream()
                .map(country -> GetCountryResponse.of(country.getName(), country.getId()))
                .collect(Collectors.toList());

        return responseList;
    }




}
