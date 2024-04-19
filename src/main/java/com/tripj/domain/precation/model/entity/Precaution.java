package com.tripj.domain.precation.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Precaution extends BaseTimeEntity {

    @Id
    @Column(name = "precaution_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private String title;

    private String content;



}
