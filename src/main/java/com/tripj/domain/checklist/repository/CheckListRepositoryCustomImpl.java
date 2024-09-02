package com.tripj.domain.checklist.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetMyCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetMyCheckListResponse;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.domain.item.model.entity.QFixedItem;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tripj.domain.checklist.model.entity.QCheckList.*;
import static com.tripj.domain.item.model.entity.QFixedItem.fixedItem;
import static com.tripj.domain.item.model.entity.QItem.item;
import static com.tripj.domain.itemcate.model.entity.QItemCate.itemCate;
import static com.tripj.domain.trip.model.entity.QTrip.*;

@Repository
public class CheckListRepositoryCustomImpl implements CheckListRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CheckListRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<GetCheckListResponse> getCheckList(Long userId, Long tripId) {
        List<GetCheckListResponse> results = queryFactory
                .select(new QGetCheckListResponse(
                        checkList.id,
                        item.id,
                        checkList.user.id,
                        new CaseBuilder()
                                .when(checkList.itemType.eq(ItemType.USER_ADDED))
                                .then(item.itemName)
                                .when(checkList.itemType.eq(ItemType.FIXED))
                                .then(fixedItem.itemName)
                                .otherwise("미지정 아이템")
                                .as("itemName"),
                        itemCate.id,
                        checkList.pack,
                        checkList.itemType
                ))
                .from(checkList)
                .join(checkList.item, item)
                .leftJoin(fixedItem).on(fixedItem.id.eq(checkList.item.id).and(checkList.itemType.eq(ItemType.FIXED)))
                .join(item.itemCate, itemCate)
                .join(checkList.trip, trip)
                .where(
                        trip.id.eq(tripId),
                        checkList.user.id.eq(userId)
                )
                .fetch();

        return results;
    }


}
