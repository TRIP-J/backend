package com.tripj.domain.checklist.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.GetCheckListResponse;
import com.tripj.domain.checklist.model.dto.QGetCheckListResponse;

import java.util.List;

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
}
