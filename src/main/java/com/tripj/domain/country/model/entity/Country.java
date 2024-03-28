package com.tripj.domain.country.model.entity;

import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "country")
    private List<Item> items = new ArrayList<>();

    private String countryName;

    private String countryCode;

}
