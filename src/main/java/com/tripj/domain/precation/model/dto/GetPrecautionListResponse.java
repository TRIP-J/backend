package com.tripj.domain.precation.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "주의사항 조회 DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetPrecautionListResponse {

    @Schema(description = "주의사항 Id", example = "1")
    private Long id;

    @Schema(description = "주의사항 제목",
            example = "일본은 현금을 많이 사용해요")
    private String title;

    @Schema(description = "주의사항 내용",
            example = "일본은 카드보다 현금을 많이 사용하는 편이에요....")
    private String content;

    @Schema(description = "나라명", example = "일본")
    private String countryName;

}
