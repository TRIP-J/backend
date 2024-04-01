package com.tripj.domain.checklist.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PackCheckListResponse {

    private Long checkListId;

    public static PackCheckListResponse of(Long checkListId) {
        return new PackCheckListResponse(checkListId);
    }

}
