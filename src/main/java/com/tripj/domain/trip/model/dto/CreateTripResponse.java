package com.tripj.domain.trip.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    public static CreateTripResponse of(Long tripId) {
        return new CreateTripResponse(tripId);
    }
}
