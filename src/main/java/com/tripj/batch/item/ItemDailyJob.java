package com.tripj.batch.item;

import com.tripj.domain.item.service.ItemService;
import com.tripj.domain.trip.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDailyJob {

    private final ItemService itemService;

    public void run() {
        itemService.changeItemPrevious();
    }




}
