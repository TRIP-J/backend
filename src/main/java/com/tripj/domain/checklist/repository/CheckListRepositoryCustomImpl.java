package com.tripj.domain.checklist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.response.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.GetMyCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetCheckListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetMyCheckListResponse;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tripj.domain.checklist.model.entity.QCheckList.*;
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
                        item.itemName,
                        itemCate.id,
                        checkList.pack
                ))
                .from(checkList)
                .join(checkList.item, item)
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
