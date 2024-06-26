package com.tripj.domain.checklist.model.dto.response;

import com.tripj.domain.checklist.model.entity.CheckList;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PackCheckListResponse {

    private Long checklistId;
    private String pack;

    public static PackCheckListResponse of(CheckList checkList) {
        return new PackCheckListResponse(checkList.getId(), checkList.getPack());
    }

}
