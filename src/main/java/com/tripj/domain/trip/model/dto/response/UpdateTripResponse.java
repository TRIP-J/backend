package com.tripj.domain.trip.model.dto.response;

import com.tripj.domain.trip.model.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    @Schema(description = "여행 이름", example = "즐거운 오사카 여행")
    private String tripName;

    @Schema(description = "여행 목적", example = "여행")
    private String purpose;

    @Schema(description = "지난 여행 분류", example = "NOW")
    private String previous;

    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull(message = "나라 선택을 필수로 해주세요")
    @Schema(description = "나라 ID", example = "1")
    private Long countryId;

    public static UpdateTripResponse of(Trip trip) {
        return new UpdateTripResponse(trip.getId(), trip.getTripName(), trip.getPurpose(), trip.getPrevious(),
                                      trip.getStartDate(), trip.getEndDate(), trip.getCountry().getId());
    }
}
