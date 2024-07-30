package com.tripj.domain.country.model.entity;

import com.tripj.domain.item.model.entity.Item;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Country {

    @Id
    @Column(name = "country_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "country")
    private List<Item> items = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private CountryCate countryCate;

    @Column(name = "country_name", nullable = false)
    private String name;

    @Column(name = "country_code", nullable = false)
    private String code;

    @Builder
    public Country(Long id, List<Item> items, String name, String code) {
        this.id = id;
        this.items = items;
        this.name = name;
        this.code = code;
    }
}
