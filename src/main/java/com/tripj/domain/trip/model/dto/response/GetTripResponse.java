package com.tripj.domain.trip.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Schema(description = "현재 여행 정보 조회 DTO")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "나라명", example = "일본")
    private String countryName;

    @Schema(description = "여행 이름", example = "즐거운 오사카 여행")
    private String tripName;

    @Schema(description = "여행 목적", example = "여유로운 휴식,휴가")
    private String purpose;

    @Schema(description = "여행 시작일", example = "2024-03-30")
    private LocalDate startDate;

    @Schema(description = "여행 종료일", example = "2024-04-01")
    private LocalDate endDate;

    @QueryProjection
    public GetTripResponse(Long tripId, Long userId, String countryName,
                           String tripName, String purpose,
                           LocalDate startDate, LocalDate endDate) {
        this.tripId = tripId;
        this.userId = userId;
        this.countryName = countryName;
        this.tripName = tripName;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
    }



}
