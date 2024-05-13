package com.tripj.domain.checklist.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class PackCheckListRequest {

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checklistId;
}