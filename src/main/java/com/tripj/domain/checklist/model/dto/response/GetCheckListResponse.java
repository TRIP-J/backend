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

    @Schema(description = "체크리스트 Id", example = "1")
    private Long checkListId;

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    @Schema(description = "유저 Id", example = "1")
    private Long userId;

    @Schema(description = "아이템명", example = "동전지갑")
    private String itemName;

    @Schema(description = "아이템 카테고리 명", example = "추천템")
    private String itemCateName;

    @Schema(description = "아이템 고정 여부", example = "N")
    private String fix;

    @Schema(description = "아이템 챙김 여부", example = "NO")
    private String pack;

    @QueryProjection
    public GetCheckListResponse(Long checkListId, Long itemId, Long userId, String itemName, String itemCateName, String fix, String pack) {
        this.checkListId = checkListId;
        this.itemId = itemId;
        this.userId = userId;
        this.itemName = itemName;
        this.itemCateName = itemCateName;
        this.fix = fix;
        this.pack = pack;
    }

}
