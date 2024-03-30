package com.tripj.domain.country.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCountryResponse {

    @Schema(description = "나라명", example = "홍콩")
    private String countryName;

    @Schema(description = "나라 id", example = "1")
    private Long countryId;

    public static GetCountryResponse of(String countryName, Long countryId) {
        return new GetCountryResponse(countryName, countryId);
    }
}
