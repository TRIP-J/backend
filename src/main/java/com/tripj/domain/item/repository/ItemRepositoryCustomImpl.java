package com.tripj.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetItemListResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tripj.domain.checklist.model.entity.QCheckList.checkList;
import static com.tripj.domain.item.model.entity.QItem.item;
import static com.tripj.domain.itemcate.model.entity.QItemCate.itemCate;
import static com.tripj.domain.trip.model.entity.QTrip.trip;

@Repository
public class ItemRepositoryCustomImpl implements ItemRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public ItemRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<GetItemListResponse> getItemList(Long userId) {
        List<GetItemListResponse> tripItems = queryFactory
                .select(new QGetItemListResponse(
                        item.id,
                        item.itemName
                ))
                .from(item)
                .join(item.trip, trip)
                .where(
                        trip.previous.eq("NOW")
                )
                .fetch();

        // fix가 'F'인 아이템
        List<GetItemListResponse> fixedItems = queryFactory
                .select(new QGetItemListResponse(
                        item.id,
                        item.itemName
                ))
                .from(item)
                .where(
                        item.fix.eq("F")
                )
                .fetch();

        tripItems.addAll(fixedItems);
        return tripItems;
    }
}
