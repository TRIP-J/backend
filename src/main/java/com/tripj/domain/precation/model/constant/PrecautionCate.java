package com.tripj.domain.precation.model.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PrecautionCate {
    CULTURE("CULTURE", "현지 문화"),
    TRAFFIC("TRAFFIC", "교통 정보"),
    CONTACT("CONTACT", "현지 연락처"),
    ACCIDENT("ACCIDENT", "사건 및 사고");

    private final String code;
    private final String name;
}
