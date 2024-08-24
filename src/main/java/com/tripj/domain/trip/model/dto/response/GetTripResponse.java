package com.tripj.domain.trip.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@Schema(description = "현재 여행 정보 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    @Schema(description = "나라 ID", example = "1")
    private Long countryId;

    @Schema(description = "나라명", example = "일본")
    private String countryName;

    @Schema(description = "현재/이전 여행 분기 컬럼", example = "NOW")
    private String previous;

    @Schema(description = "여행 시작일", example = "2024-03-30")
    private LocalDate startDate;

    @Schema(description = "여행 종료일", example = "2024-04-01")
    private LocalDate endDate;

    @QueryProjection
    public GetTripResponse(Long tripId, Long countryId,
                           String countryName, String previous,
                           LocalDate startDate, LocalDate endDate) {
        this.tripId = tripId;
        this.countryId = countryId;
        this.countryName = countryName;
        this.previous = previous;
        this.startDate = startDate;
        this.endDate = endDate;
    }



}
