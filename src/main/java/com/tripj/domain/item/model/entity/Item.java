package com.tripj.domain.item.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.item.constant.ItemStatus;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Item extends BaseTimeEntity {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_cate_id")
    private ItemCate itemCate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixed_item_id")
    private FixedItem fixedItem;

    private String itemName;

    private String fixedItemDelYN;

//    @Enumerated(EnumType.STRING)
//    private ItemStatus itemStatus;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;

    public static Item newItem(String itemName, User user,
                               ItemCate itemCate, Trip trip) {
        return Item.builder()
                .itemName(itemName)
                .user(user)
                .itemCate(itemCate)
                .trip(trip)
                .build();
    }

    /**
     * 아이템 수정
     */
    public void updateItem(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 아이템 타입 추가
     */
    public void updateItemType(ItemType itemType) {
        this.itemType = itemType;
    }


    /**
     * 아이템 상태 변경
     */
//    public void updateItemStatus(ItemStatus itemStatus) {
//        this.itemStatus = itemStatus;
//    }

//    public void updatePrevious(String previous) {
//        this.previous = previous;
//    }

}
