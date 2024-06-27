package com.tripj.domain.itemcate.model.entity;

import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ItemCate {

    @Id
    @Column(name = "item_cate_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @OneToMany(mappedBy = "itemCate")
    private List<Item> items = new ArrayList<>();

    private String itemCateName;

    private String itemCateCode;

    @Builder
    public ItemCate(Long id, List<Item> items, String itemCateName, String itemCateCode) {
        this.Id = id;
        this.items = items;
        this.itemCateName = itemCateName;
        this.itemCateCode = itemCateCode;
    }
}
