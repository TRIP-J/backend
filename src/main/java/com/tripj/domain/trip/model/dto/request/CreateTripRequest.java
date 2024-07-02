package com.tripj.domain.trip.model.dto.request;

import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.trip.model.entity.Trip;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateTripRequest {

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

    public Trip toEntity(User user, Country country) {
        return Trip.newTrip(tripName, purpose, previous,
                            startDate, endDate, user, country);


    }



}
