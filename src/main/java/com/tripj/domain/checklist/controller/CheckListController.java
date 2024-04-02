package com.tripj.domain.checklist.controller;

import com.tripj.domain.checklist.model.dto.*;
import com.tripj.domain.checklist.service.CheckListService;
import com.tripj.global.model.RestApiResponse;
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
    public RestApiResponse <List<GetCheckListResponse>> getCheckList(Long itemCateId,
                                                                     Long userId) {
        return RestApiResponse.success(checkListService.getCheckList(itemCateId, userId));
    }

    @Operation(
            summary = "체크리스트 추가 API",
            description = "체크리스트에 아이템을 추가합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateCheckListResponse> createCheckList(@RequestBody CreateCheckListRequest request,
                                                                    Long userId) {
        return RestApiResponse.success(
                checkListService.createCheckList(request, userId));
    }

    @Operation(
            summary = "체크리스트 삭제 API",
            description = "체크리스트에 아이템을 삭제합니다."
    )
    @DeleteMapping("/{checkListId}")
    public RestApiResponse<DeleteCheckListResponse> deleteChecList(@PathVariable("checkListId") Long checkListId,
                                                                   Long userId) {
        return RestApiResponse.success(
                checkListService.deleteCheckList(checkListId, userId));
    }

    @Operation(
            summary = "체크리스트 챙김 API",
            description = "체크리스트에 추가한 아이템 체크박스 클릭시 챙김여부 변경"
    )
    @PostMapping("/pack")
    public RestApiResponse<PackCheckListResponse> packCheckList(@RequestBody PackCheckListRequest request,
                                                                Long userId) {
        return RestApiResponse.success(
                checkListService.packCheckList(request, userId));
    }
    
    @Operation(
            summary = "체크리스트에 담은 아이템 조회 API",
            description = "체크리스트 > 체크리스트에 내가 담은 아이템 카테고리별 조회"
    )
    @GetMapping("/checklist")
    public RestApiResponse <List<GetMyCheckListResponse>> getMyCheckList(Long itemCateId,
                                                                         Long userId) {
        return RestApiResponse.success(checkListService.getMyCheckList(itemCateId, userId));
    }







}
