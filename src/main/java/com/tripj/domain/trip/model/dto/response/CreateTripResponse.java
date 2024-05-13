package com.tripj.domain.trip.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    public static CreateTripResponse of(Long tripId) {
        return new CreateTripResponse(tripId);
    }
}