package com.tripj.batch.trip;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Profile("batch")
@RequiredArgsConstructor
public class TripBatchScheduler {

    private final TripDailyJob tripDailyJob;

    /**
     * 매일 자정 여행계획의 EndDate 가 지나면 Previous 상태 변경
     */
    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(fixedDelay = 60000)
    public void runDailyJobPrevious() {
        log.info("tripDailyJob Update batch execute.");
        tripDailyJob.run();
    }


}
