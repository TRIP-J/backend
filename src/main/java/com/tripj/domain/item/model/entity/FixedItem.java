package com.tripj.domain.item.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.item.constant.ItemType;
import com.tripj.domain.itemcate.model.entity.ItemCate;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FixedItem extends BaseTimeEntity {

    @Id
    @Column(name = "fixed_item_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_cate_id")
    private ItemCate itemCate;

    private String itemName;

    @Enumerated(EnumType.STRING)
    private ItemType itemType;
}
