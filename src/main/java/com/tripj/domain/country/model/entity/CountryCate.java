package com.tripj.domain.country.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CountryCate {

    ASIA("아시아", 1),
    EUROPE("유럽", 2),
    AMERICA("아메리카", 3),
    AFRICA("아프리카", 4),
    OCEANIA("오세아니아", 5),
    ANTARCTICA("남극", 6);

    private final String value;
    private final int key;
}
