package com.tripj.domain.trip.model.dto;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class CreateTripRequest {

    @NotNull(message = "여행 이름은 필수로 입력해주세요")
    private String tripName;

    private String purpose;

    private String previous;

    private LocalDate startDate;

    private LocalDate endDate;

    private Long countryId;

    public Trip toEntity(User user, Country country) {
        return Trip.newTrip(tripName, purpose, previous,
                            startDate, endDate, user, country);


    }



}
