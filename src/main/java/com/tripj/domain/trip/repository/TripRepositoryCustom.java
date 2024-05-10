package com.tripj.domain.trip.repository;


import com.tripj.domain.trip.model.dto.response.GetTripResponse;

import java.util.List;

public interface TripRepositoryCustom {

    GetTripResponse getTrip(Long userId);

    List<GetTripResponse> getPastTrip(Long userId);

}
