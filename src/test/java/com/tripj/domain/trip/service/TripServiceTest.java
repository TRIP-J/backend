package com.tripj.domain.trip.service;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.trip.repository.TripRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@ActiveProfiles("test")
class TripServiceTest {

    @Autowired
    private TripService tripService;

    @Autowired
    private TripRepository tripRepository;

    @Test
    @DisplayName("여행 계획을 최초 등록한다.")
    void createTrip_whenBeforeExistingTrip() {
        // given
        CreateTripRequest createTripRequest =
                new CreateTripRequest(
                        "즐거운 오사카 여행",
                        "여행",
                        "NOW",
                        LocalDate.of(2022, 10, 1),
                        LocalDate.of(2022, 10, 10),
                        1L
                );

        // when
        CreateTripResponse trip = tripService.createTrip(createTripRequest, 1L);

        // then
        assertNotNull(trip);
        assertEquals("즐거운 오사카 여행", trip.getTripName());
        assertEquals("여행", trip.getPurpose());
        assertEquals("NOW", trip.getPrevious());
        assertEquals(LocalDate.of(2022, 10, 1), trip.getStartDate());
        assertEquals(LocalDate.of(2022, 10, 10), trip.getEndDate());

    }


}