package com.tripj.domain.trip.model.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Schema(description = "여행 횟수 조회 DTO")
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetTripCountResponse {

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "여행 횟수", example = "3")
    private Long tripCount;

    public static GetTripCountResponse of(Long userId, Long tripCount) {
        return new GetTripCountResponse(userId, tripCount);

    }

}
