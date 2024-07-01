package com.tripj.domain.trip.controller;

import com.tripj.domain.trip.model.dto.request.CreateTripRequest;
import com.tripj.domain.trip.model.dto.response.CreateTripResponse;
import com.tripj.domain.trip.model.dto.response.GetTripCountResponse;
import com.tripj.domain.trip.model.dto.response.GetTripResponse;
import com.tripj.domain.trip.model.dto.request.UpdateTripRequest;
import com.tripj.domain.trip.model.dto.response.UpdateTripResponse;
import com.tripj.domain.trip.service.TripService;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.model.RestApiResponse;
import com.tripj.resolver.userinfo.UserInfo;
import com.tripj.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(
            summary = "여행 등록 API",
            description = "나라 선택 후 여행을 등록합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateTripResponse> createTrip(
            @RequestBody @Valid CreateTripRequest request,
            @UserInfo UserInfoDto userInfo) {

//        if (bindingResult.hasErrors()) {
//            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
//            return RestApiResponse.error(ErrorCode.E400_BINDING_RESULT, errorMessage);
//        }

        return RestApiResponse.success(
                tripService.createTrip(request, userInfo.getUserId()));
    }

    @Operation(
            summary = "여행 수정 API",
            description = "등록했던 여행을 수정합니다."
    )
    @PostMapping("/{tripId}")
    public RestApiResponse<UpdateTripResponse> updateTrip(
            @RequestBody @Validated UpdateTripRequest request,
            @PathVariable Long tripId,
            @UserInfo UserInfoDto userInfo,
            BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return RestApiResponse.error(ErrorCode.E400_BINDING_RESULT, errorMessage);
        }

        return RestApiResponse.success(
                tripService.updateTrip(request, tripId, userInfo.getUserId()));
    }


   	@Operation(
   				summary = "여행 조회 API",
   				description = "여행 선택완료 후 메인페이지에 조회되는 여행 정보 조회"
   	)
   	@GetMapping("")
    public RestApiResponse<GetTripResponse> getTrip(
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                tripService.getTrip(userInfo.getUserId()));
    }

    @Operation(
            summary = "지난 여행 기록 조회 API",
            description = "자신의 지난 여행 기록 모두 조회"
    )
    @GetMapping("/past")
    public RestApiResponse<List<GetTripResponse>> getPastTrip(
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                tripService.getPastTrip(userInfo.getUserId()));
    }

    @Operation(
            summary = "여행 횟수 조회 API",
            description = "몇 번째 여행 준비중 조회"
    )
    @GetMapping("/count")
    public RestApiResponse<GetTripCountResponse> getTripCount(
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(tripService.getTripCount(userInfo.getUserId()));
    }



}
