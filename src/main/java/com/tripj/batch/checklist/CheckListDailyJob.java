package com.tripj.batch.checklist;

import com.tripj.domain.checklist.service.CheckListService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CheckListDailyJob {

    private final CheckListService checkListService;

    public void run() {
        checkListService.changeCheckListPrevious();
    }




}
