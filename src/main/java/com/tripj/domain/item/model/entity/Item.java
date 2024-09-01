package com.tripj.domain.item.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.item.constant.ItemStatus;
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

    private String itemName;

    private String fix;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    public static Item newItem(String itemName, User user,
                               ItemCate itemCate, Trip trip, String fix) {
        return Item.builder()
                .itemName(itemName)
                .user(user)
                .itemCate(itemCate)
                .trip(trip)
                .fix(fix)
                .build();
    }

    /**
     * 아이템 수정
     */
    public void updateItem(String itemName) {
        this.itemName = itemName;
    }

    /**
     * 아이템 상태 변경
     */
    public void updateItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

//    public void updatePrevious(String previous) {
//        this.previous = previous;
//    }

}
