package com.tripj.domain.checklist.model.dto.response;

import com.tripj.domain.checklist.model.entity.CheckList;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCheckListResponse {

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checkListId;

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemId;

    @Schema(description = "여행 Id", example = "1")
    private Long tripId;

    public static CreateCheckListResponse of(CheckList checkList) {
        return new CreateCheckListResponse(checkList.getId(), checkList.getItem().getId(), checkList.getTrip().getId());
    }

}
