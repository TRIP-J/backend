package com.tripj.domain.trip.repository;


import com.tripj.domain.trip.model.dto.GetTripResponse;
import com.tripj.domain.trip.model.entity.Trip;

import java.util.List;
import java.util.Optional;

public interface TripRepositoryCustom {

    GetTripResponse getTrip(Long userId);

    List<GetTripResponse> getPastTrip(Long userId);

}
