package com.tripj.domain.checklist.model.dto;

import lombok.Getter;

@Getter
public class PackCheckListRequest {
    private Long itemId;
    private Long checklistId;
    private String pack;
}
