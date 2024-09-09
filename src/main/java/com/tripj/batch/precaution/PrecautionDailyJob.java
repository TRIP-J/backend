package com.tripj.batch.precaution;

import com.tripj.external.crawling.service.PrecautionCrawling;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class PrecautionDailyJob {

    private final PrecautionCrawling precautionCrawling;

    public void run() {
        precautionCrawling.precautionCrawling();
    }
}
