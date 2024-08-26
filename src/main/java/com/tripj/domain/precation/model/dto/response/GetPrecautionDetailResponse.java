package com.tripj.domain.precation.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "주의사항 상세조회 DTO")
public class GetPrecautionDetailResponse {

    @Schema(description = "현지 연락처",
            example = "대표번호(근무시간 중) : (86) 10-8531-0700")
    private String contact;

    @Schema(description = "교통 정보",
            example = "주요 교통 법규 및 문화\n" +
                    "ㅇ 운전석 좌측, 우측 주행, 직진 차량 우선..")
    private String traffic;

    @Schema(description = "현지문화",
            example = "일반 문화\n" +
                    "ㅇ 중국인들은 인사할 때 주로 인사말만 건네는 것이 대부분이며, 남자들은 한국과 마찬가지로 악수를 하는 경우가 많습니다.")
    private String culture;

    @Schema(description = "사건 사고",
            example = "주거 침입 절도 피해가 종종 발생하고 있으니 문단속 등 철저한 사전 예방이 필요합니다.")
    private String accident;

    @QueryProjection
    public GetPrecautionDetailResponse(String contact, String traffic, String culture, String accident) {
        this.contact = contact;
        this.traffic = traffic;
        this.culture = culture;
        this.accident = accident;
    }
}
