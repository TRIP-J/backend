package com.tripj.domain.trip.model.dto.response;

import com.tripj.domain.trip.model.entity.Trip;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTripResponse {

    @Schema(description = "여행 ID", example = "1")
    private Long tripId;

    @NotNull(message = "여행 이름은 필수로 입력해 주세요")
    @Schema(description = "여행 이름", example = "즐거운 오사카 여행")
    private String tripName;

    @NotNull(message = "여행 목적은 필수로 입력해 주세요")
    @Schema(description = "여행 목적", example = "여행")
    private String purpose;

    @Schema(description = "지난 여행 분류", example = "NOW")
    private String previous;

    @NotNull(message = "시작일은 필수로 입력해 주세요")
    private LocalDate startDate;

    @NotNull(message = "종료일은 필수로 입력해 주세요")
    private LocalDate endDate;

    @NotNull(message = "나라 선택을 필수로 해주세요")
    @Schema(description = "나라 ID", example = "1")
    private Long countryId;

    public static CreateTripResponse of(Trip trip) {
        return new CreateTripResponse(
                trip.getId(),
                trip.getTripName(),
                trip.getPurpose(),
                trip.getPrevious(),
                trip.getStartDate(),
                trip.getEndDate(),
                trip.getCountry().getId());
    }
}
