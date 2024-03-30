package com.tripj.domain.item.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.itemcate.model.entity.ItemCate;
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
    @JoinColumn(name = "country_id")
    private Country country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_cate_id")
    private ItemCate itemCate;

    private String itemName;

    private String previous;

    private String fix;

    public static Item newItem(String itemName, String previous,
                               User user, ItemCate itemCate,
                               Country country) {
        return Item.builder()
                .itemName(itemName)
                .previous("NOW")
                .user(user)
                .itemCate(itemCate)
                .country(country)
                .build();
    }
}
