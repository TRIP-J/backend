package com.tripj.domain.checklist.controller;

import com.tripj.domain.checklist.model.dto.GetCheckListResponse;
import com.tripj.domain.checklist.service.CheckListService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/checklist")
@Tag(name = "checkList", description = "체크리스트 API")
public class CheckListController {

    private final CheckListService checkListService;

    @Tag(name = "checkList")
    @Operation(
            summary = "체크리스트 조회 API",
            description = "체크리스트 카테고리별 아이템 조회"
    )
    @GetMapping("")
    public RestApiResponse <List<GetCheckListResponse>> getCheckList(Long itemCateId,
                                                                     Long userId) {
        return RestApiResponse.success(checkListService.getCheckList(itemCateId, userId));
    }


}
