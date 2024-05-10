package com.tripj.domain.country.controller;

import com.tripj.domain.country.model.dto.response.GetCountryResponse;
import com.tripj.domain.country.service.CountryService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/country")
@Tag(name = "country", description = "나라 API")
public class CountryController {

    private final CountryService countryService;

    @Tag(name = "country")
    @Operation(
            summary = "나라 조회 API",
            description = "나라 전체를 조회합니다."
    )
    @GetMapping("")
    public RestApiResponse <List<GetCountryResponse>> getCountry() {
        return RestApiResponse.success(countryService.getCountry());
    }




}
