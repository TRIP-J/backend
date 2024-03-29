package com.tripj.domain.trip.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTripResponse {

    private Long tripId;

    public static CreateTripResponse of(Long tripId) {
        return new CreateTripResponse(tripId);
    }
}
