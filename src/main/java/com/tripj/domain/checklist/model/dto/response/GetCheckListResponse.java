package com.tripj.domain.checklist.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "체크리스트 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetCheckListResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "나라 ID", example = "1")
    private Long countryId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 카테고리 명", example = "추천템")
    private String itemCateName;

    @Schema(description = "아이템 고정 여부", example = "N")
    private String fix;

    @QueryProjection
    public GetCheckListResponse(Long itemId, Long userId, Long countryId, String itemName, String itemCateName, String fix) {
        this.itemId = itemId;
        this.userId = userId;
        this.countryId = countryId;
        this.itemName = itemName;
        this.itemCateName = itemCateName;
        this.fix = fix;
    }

}
