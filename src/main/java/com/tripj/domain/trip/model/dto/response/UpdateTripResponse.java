package com.tripj.domain.trip.model.dto.response;

import com.tripj.domain.trip.model.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    @Schema(description = "지난 여행 분류", example = "NOW")
    private String previous;

    @Schema(description = "여행 시작일", example = "2024-03-30")
    private LocalDate startDate;

    @Schema(description = "여행 종료일", example = "2024-04-30")
    private LocalDate endDate;

    @Schema(description = "나라 ID", example = "1")
    private Long countryId;

    @Schema(description = "나라명", example = "홍콩")
    private String countryName;

    public static UpdateTripResponse of(Trip trip) {
        return new UpdateTripResponse(
                trip.getId(), trip.getPrevious(),
                trip.getStartDate(), trip.getEndDate(),
                trip.getCountry().getId(),
                trip.getCountry().getName());
    }
}
