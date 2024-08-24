package com.tripj.batch.item;

import com.tripj.batch.trip.TripDailyJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
//@Profile("batch")
@RequiredArgsConstructor
public class ItemBatchScheduler {

//    private final ItemDailyJob itemDailyJob;
//
//    /**
//     * 매일 자정 여행계획의 EndDate 가 지나면 Previous 상태 변경
//     */
//    @Scheduled(cron = "0 0 12 * * ?")
//    public void runDailyJobPrevious() {
//        log.info("itemDailyJob Update batch execute");
//        itemDailyJob.run();
//    }


}
