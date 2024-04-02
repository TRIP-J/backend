package com.tripj.domain.checklist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.*;
import com.tripj.domain.checklist.model.entity.QCheckList;

import java.util.List;

import static com.tripj.domain.checklist.model.entity.QCheckList.*;
import static com.tripj.domain.item.model.entity.QItem.item;
import static com.tripj.domain.itemcate.model.entity.QItemCate.itemCate;

public class CheckListRepositoryCustomImpl implements CheckListRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public CheckListRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public List<GetCheckListResponse> getCheckList(Long itemCateId, Long userId) {
        List<GetCheckListResponse> results = queryFactory
                .select(new QGetCheckListResponse(
                        item.id,
                        item.itemName
                ))
                .from(item)
                .join(item.itemCate, itemCate)
                .where(
                        item.itemCate.Id.eq(itemCateId)
                        .and(item.fix.eq("F")
                            .or(item.previous.eq("NOW")
                            .and(item.user.id.eq(userId)))
                            )
                )
                .fetch();

        return results;
    }

    @Override
    public List<GetMyCheckListResponse> getMyCheckList(Long itemCateId, Long userId) {
        List<GetMyCheckListResponse> results = queryFactory
                        .select(new QGetMyCheckListResponse(
                                checkList.id,
                                checkList.item.itemName,
                                itemCate.itemCateName
                        ))
                        .from(checkList)
                        .join(checkList.item, item)
                        .join(item.itemCate, itemCate)
                        .where(
                                item.itemCate.Id.eq(itemCateId),
                                checkList.user.id.eq(userId)
                        )
                        .fetch();

        return results;
    }
}
