package com.tripj.batch.trip;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Profile("batch")
@RequiredArgsConstructor
public class TripBatchScheduler {

    private final TripDailyJob tripDailyJob;

    /**
     * 매일 자정 여행계획의 EndDate 가 지나면 Previous 상태 변경
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void runDailyJobPrevious() {
        tripDailyJob.run();
    }



}
