package com.tripj.domain.checklist.model.dto.request;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Schema(description = "나의 체크리스트 조회 DTO")
public class GetMyCheckListRequest {

    @Schema(description = "아이템 카테고리 Id", example = "1")
    private Long itemCateId;
}
