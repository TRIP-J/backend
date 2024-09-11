package com.tripj.domain.precation.model.dto.response;

import com.tripj.domain.precation.model.entity.Precaution;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "주의사항 조회 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPrecautionListResponse {

    @Schema(description = "카테고리별 주의사항", example = "카테고리별 주의사항 예시")
    private String content;

    public static GetPrecautionListResponse of(String content) {
        return new GetPrecautionListResponse(content);
    }
}
