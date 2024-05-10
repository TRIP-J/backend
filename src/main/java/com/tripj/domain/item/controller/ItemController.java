package com.tripj.domain.item.controller;

import com.tripj.domain.item.model.dto.request.CreateItemRequest;
import com.tripj.domain.item.model.dto.response.CreateItemResponse;
import com.tripj.domain.item.service.ItemService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
@Tag(name = "item", description = "아이템 API")
public class ItemController {

    private final ItemService itemService;

    @Operation(
            summary = "아이템 등록 API",
            description = "체크리스트에서 아이템을 등록합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateItemResponse> createItem(@RequestBody CreateItemRequest request,
                                                          Long userId) {

        return RestApiResponse.success(itemService.createItem(request, userId));
    }





}
