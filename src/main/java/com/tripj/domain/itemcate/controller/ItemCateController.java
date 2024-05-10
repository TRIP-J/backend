package com.tripj.domain.itemcate.controller;

import com.tripj.domain.itemcate.model.dto.response.GetItemCateResponse;
import com.tripj.domain.itemcate.service.ItemCateService;
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
@RequestMapping("/api/itemcate")
@Tag(name = "itemCate", description = "아이템 카테고리 API")
public class ItemCateController {

    private final ItemCateService itemCateService;

    @Tag(name = "itemCate")
    @Operation(
            summary = "아이템 카테고리 조회 API",
            description = "체크리스트 클릭시 아이템 카테고리를 조회 합니다."
    )
    @GetMapping("")
    public RestApiResponse<List<GetItemCateResponse>> getItemCate() {
        return RestApiResponse.success(itemCateService.getItemCate());
    }
}
