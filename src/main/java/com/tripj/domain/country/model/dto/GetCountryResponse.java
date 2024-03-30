package com.tripj.domain.country.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCountryResponse {

    @Schema(description = "나라명", example = "홍콩")
    private String countryName;

    @Schema(description = "나라 코드", example = "HK")
    private String countryCode;

    public static GetCountryResponse of(String countryName, String countryCode) {
        return new GetCountryResponse(countryName, countryCode);
    }
}
