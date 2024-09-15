package com.tripj.domain.item.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SetItemCate {
    BASIC("기본 세트"),
    PEDDLER("보부상 세트"),
    MINIMAL("미니멀 세트");

    private final String description;
}