package com.tripj.batch.trip;

import com.tripj.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TripDailyJob {

    private final TripService tripService;

    public void run() {
        tripService.changeTripPrevious();
    }




}
