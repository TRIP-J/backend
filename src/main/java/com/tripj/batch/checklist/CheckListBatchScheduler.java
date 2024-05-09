package com.tripj.batch.checklist;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Profile("batch")
@RequiredArgsConstructor
public class CheckListBatchScheduler {

    private final CheckListDailyJob checkListDailyJob;

    /**
     * 매일 자정 여행계획의 EndDate 가 지나면 Previous 상태 변경
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public void runDailyJobPrevious() {
        log.info("checkListDailyJob Update batch execute");
        checkListDailyJob.run();
    }


}
