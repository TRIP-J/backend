package com.tripj.domain.trip.controller;

import com.tripj.domain.trip.model.dto.CreateTripRequest;
import com.tripj.domain.trip.model.dto.CreateTripResponse;
import com.tripj.domain.trip.model.dto.GetTripResponse;
import com.tripj.domain.trip.service.TripService;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trip")
@Tag(name = "trip", description = "여행 API")
public class TripController {

    private final TripService tripService;

    @Tag(name = "trip")
    @Operation(
            summary = "여행 등록 API",
            description = "나라 선택 후 여행을 등록합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateTripResponse> createTrip(@RequestBody @Validated CreateTripRequest request,
                                                          Long userId,
                                                          BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return RestApiResponse.error(ErrorCode.E401_BINDING_RESULT, errorMessage);
        }

        return RestApiResponse.success(
                tripService.createTrip(request, userId));
    }

    @Tag(name = "trip")
   	@Operation(
   				summary = "여행 조회 API",
   				description = "여행 선택완료 후 메인페이지에 조회되는 여행 정보 조회"
   	)
   	@GetMapping("")
    public RestApiResponse <List<GetTripResponse>> getTrip(Long userId) {

        return RestApiResponse.success(
                tripService.getTrip(userId));
    }



}
