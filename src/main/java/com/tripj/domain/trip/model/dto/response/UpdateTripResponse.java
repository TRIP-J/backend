package com.tripj.domain.trip.model.dto.response;

import com.tripj.domain.trip.model.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    public static UpdateTripResponse of(Trip trip) {
        return new UpdateTripResponse(trip.getId());
    }
}
