package com.tripj.domain.checklist.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PackCheckListResponse {

    private Long id;
    private String pack;

    public static PackCheckListResponse of(Long checklistId, String pack) {
        return new PackCheckListResponse(checklistId, pack);
    }

}
