package com.tripj.domain.trip.repository;


import com.tripj.domain.trip.model.dto.GetTripResponse;

import java.util.List;

public interface TripRepositoryCustom {

    List<GetTripResponse> getTrip(Long userId);
    List<GetTripResponse> getPastTrip(Long userId);
}
