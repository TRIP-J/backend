package com.tripj.domain.checklist.controller;

import com.tripj.domain.checklist.model.dto.request.CreateCheckListRequest;
import com.tripj.domain.checklist.model.dto.response.*;
import com.tripj.domain.checklist.service.CheckListService;
import com.tripj.global.model.RestApiResponse;
import com.tripj.resolver.userinfo.UserInfo;
import com.tripj.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist")
@Tag(name = "checkList", description = "체크리스트 API")
public class CheckListController {

    private final CheckListService checkListService;

    @Operation(
            summary = "카테고리별 아이템 조회 API",
            description = "체크리스트 > 카테고리별 아이템 조회"
    )
    @GetMapping("")
    public RestApiResponse <List<GetCheckListResponse>> getCheckList(
            @UserInfo UserInfoDto userInfo,
            Long itemCateId,
            Long countryId) {

        return RestApiResponse.success(
                checkListService.getCheckList(itemCateId, userInfo.getUserId(), countryId));
    }

    @Operation(
            summary = "체크리스트 추가 API",
            description = "체크리스트에 아이템을 추가합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateCheckListResponse> createCheckList(
            @RequestBody CreateCheckListRequest request,
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                checkListService.createCheckList(request, userInfo.getUserId()));
    }

    @Operation(
            summary = "체크리스트 삭제 API",
            description = "체크리스트에 아이템을 삭제합니다."
    )
    @DeleteMapping("/{checklistId}")
    public RestApiResponse<DeleteCheckListResponse> deleteCheckList(
            @PathVariable("checklistId") Long checklistId,
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                checkListService.deleteCheckList(checklistId, userInfo.getUserId()));
    }

    @Operation(
            summary = "체크리스트 챙김 API",
            description = "체크리스트에 추가한 아이템 체크박스 클릭시 챙김여부 변경"
    )
    @PostMapping("/pack/{checkListId}")
    public RestApiResponse<PackCheckListResponse> packCheckList(
            @PathVariable("checkListId") Long checkListId,
            @UserInfo UserInfoDto userInfo) {

        return RestApiResponse.success(
                checkListService.packCheckList(checkListId, userInfo.getUserId()));
    }

    @Hidden
    @Operation(
            summary = "체크리스트에 담은 아이템 조회 API",
            description = "체크리스트 > 체크리스트에 내가 담은 아이템 카테고리별 조회"
    )
    @GetMapping("/added")
    public RestApiResponse <List<GetMyCheckListResponse>> getMyCheckList(
            @UserInfo UserInfoDto userInfo,
            Long itemCateId,
            Long tripId) {
        return RestApiResponse.success(
                checkListService.getMyCheckList(itemCateId, userInfo.getUserId(), tripId));
    }

}
