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
                        item.itemName,
                        itemCate.id,
                        item.fix
                ))
                .from(item)
                .join(item.trip, trip)
                .join(item.itemCate, itemCate)
                .where(
                        trip.previous.eq("NOW"),
                        item.user.id.eq(userId)
                )
                .fetch();

        // fix가 'F'인 아이템
        List<GetItemListResponse> fixedItems = queryFactory
                .select(new QGetItemListResponse(
                        item.id,
                        item.itemName,
                        itemCate.id,
                        item.fix
                ))
                .from(item)
                .join(item.itemCate, itemCate)
                .where(
                        item.fix.eq("F")
                )
                .fetch();

        tripItems.addAll(fixedItems);
        return tripItems;
    }
}
