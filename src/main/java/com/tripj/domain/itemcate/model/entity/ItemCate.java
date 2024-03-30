package com.tripj.domain.itemcate.model.entity;

import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class ItemCate {

    @Id
    @Column(name = "item_cate_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    @OneToMany(mappedBy = "itemCate")
    private List<Item> items = new ArrayList<>();

    private String itemCateName;

    private String itemCateCode;

}
