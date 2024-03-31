package com.tripj.domain.checklist.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DeleteCheckListResponse {

    private Long checkListId;

    public static DeleteCheckListResponse of(Long checkListId) {
        return new DeleteCheckListResponse(checkListId);
    }
}
