package com.tripj.batch.precaution;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Profile("batch")
@RequiredArgsConstructor
public class PrecautionBatchScheduler {

    private final PrecautionDailyJob precautionDailyJob;

    /**
     * 매일 자정 주의사항 크롤링
     */
    @Scheduled(cron = "0 0 12 * * ?")
//    @Scheduled(fixedDelay = 60000)
    public void runDailyJobPrevious() {
        log.info("PrecautionDailyJob Update batch execute.");
        precautionDailyJob.run();
    }

}
