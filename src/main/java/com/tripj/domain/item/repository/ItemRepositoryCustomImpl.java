package com.tripj.domain.item.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.checklist.model.dto.response.GetItemListResponse;
import com.tripj.domain.checklist.model.dto.response.QGetItemListResponse;
import com.tripj.domain.item.constant.FixStatus;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.tripj.domain.item.model.entity.QFixedItem.fixedItem;
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
                        item.itemType
                ))
                .from(item)
                .join(item.trip, trip)
                .join(item.itemCate, itemCate)
                .where(
                        trip.previous.eq("NOW"),
                        item.user.id.eq(userId),
                        item.fixedItemDelYN.isNull().or(item.fixedItemDelYN.ne("Y"))
                )
                .fetch();

        List<GetItemListResponse> fixedItems = queryFactory
                .select(new QGetItemListResponse(
                        fixedItem.id,
                        fixedItem.itemName,
                        itemCate.id,
                        fixedItem.itemType
                ))
                .from(fixedItem)
                .join(fixedItem.itemCate, itemCate)
                .leftJoin(item)
                .on(fixedItem.id.eq(item.fixedItem.id)
                        .and(item.user.id.eq(userId)))
                .where(
                        item.fixedItemDelYN.isNull().or(item.fixedItemDelYN.ne("Y")),
                        fixedItem.fixStatus.eq(FixStatus.valueOf("Y"))
                )
                .fetch();

        tripItems.addAll(fixedItems);

        return tripItems;
    }

    @Override
    public GetItemListResponse getItem(Long userId, Long itemId, ItemType itemType) {
        GetItemListResponse tripItem = queryFactory
                .select(new QGetItemListResponse(
                        item.id,
                        item.itemName,
                        itemCate.id,
                        item.itemType
                ))
                .from(item)
                .join(item.trip, trip)
                .join(item.itemCate, itemCate)
                .where(
                        item.id.eq(itemId),
                        item.itemType.eq(itemType),
                        trip.previous.eq("NOW"),
                        item.user.id.eq(userId),
                        item.fixedItemDelYN.isNull().or(item.fixedItemDelYN.ne("Y"))
                )
                .fetchOne();

        GetItemListResponse fixedItems = queryFactory
                .select(new QGetItemListResponse(
                        fixedItem.id,
                        fixedItem.itemName,
                        itemCate.id,
                        fixedItem.itemType
                ))
                .from(fixedItem)
                .join(fixedItem.itemCate, itemCate)
                .leftJoin(item)
                .on(fixedItem.id.eq(item.fixedItem.id)
                        .and(item.user.id.eq(userId)))
                .where(
                        fixedItem.id.eq(itemId),
                        fixedItem.itemType.eq(itemType),
                        item.fixedItemDelYN.isNull().or(item.fixedItemDelYN.ne("Y")))
                .fetchOne();

        if (tripItem != null) {
            return tripItem;
        } else if (fixedItems != null) {
            return fixedItems;
        } else {
            throw new NotFoundException(ErrorCode.E404_NOT_EXISTS_ITEM);
        }

    }

}
