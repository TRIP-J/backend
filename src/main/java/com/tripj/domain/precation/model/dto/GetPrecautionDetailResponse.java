package com.tripj.domain.precation.model.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "주의사항 상세조회 DTO")
public class GetPrecautionDetailResponse {

    @Schema(description = "주의사항 제목",
            example = "일본은 현금을 많이 사용해요")
    private String title;

    @Schema(description = "주의사항 내용",
            example = "일본은 카드보다 현금을 많이 사용하는 편이에요....")
    private String content;

    @QueryProjection
    public GetPrecautionDetailResponse(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
