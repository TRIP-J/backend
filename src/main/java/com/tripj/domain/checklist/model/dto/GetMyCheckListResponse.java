package com.tripj.domain.checklist.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "나의 체크리스트 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetMyCheckListResponse {

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checkListId;

    @Schema(description = "아이템 명", example = "속옷")
    private String itemName;

    @Schema(description = "아이템 카테고리 명", example = "의류")
    private String itemCateName;

    @Schema(description = "여행 이름", example = "즐거운 오사카 여행")
    private String tripName;

    @Schema(description = "챙김 여부", example = "YES")
    private String pack;

    @QueryProjection
    public GetMyCheckListResponse(Long checkListId,
                                  String itemName,
                                  String itemCateName,
                                  String tripName,
                                  String pack) {
        this.checkListId = checkListId;
        this.itemName = itemName;
        this.itemCateName = itemCateName;
        this.tripName = tripName;
        this.pack = pack;
    }

}
