package com.tripj.domain.checklist.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class CheckList extends BaseTimeEntity {

    @Id
    @Column(name = "checklist_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    private String checkCate;

    private String pack;

    private String previous;
}
