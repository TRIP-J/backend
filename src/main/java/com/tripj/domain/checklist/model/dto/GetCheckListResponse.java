package com.tripj.domain.checklist.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "체크리스트 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCheckListResponse {

    //TODO id추가.

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @QueryProjection
    public GetCheckListResponse(String itemName) {
        this.itemName = itemName;
    }

}
