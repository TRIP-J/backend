package com.tripj.domain.checklist.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateCheckListResponse {

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checkListId;

    public static CreateCheckListResponse of(Long checkListId) {
        return new CreateCheckListResponse(checkListId);
    }
}
