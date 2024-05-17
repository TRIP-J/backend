package com.tripj.domain.conversation.model.constant;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ConversationCate {

    BASIC("기초"),
    AIRPORT("공항"),
    HOTEL("호텔"),
    RES("식당"),
    SUBWAY("지하철"),
    SIGHT("관광지");

    private final String value;

}
