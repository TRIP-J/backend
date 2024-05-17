package com.tripj.domain.conversation.controller;

import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.conversation.model.dto.response.GetConversationListResponse;
import com.tripj.domain.conversation.service.ConversationService;
import com.tripj.domain.precation.model.dto.response.GetPrecautionDetailResponse;
import com.tripj.domain.precation.model.dto.response.GetPrecautionListResponse;
import com.tripj.domain.precation.service.PrecautionService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/conversation")
@Tag(name = "conversation", description = "회화 API")
public class ConversationController {

    private final ConversationService conversationService;

    @Operation(
            summary = "회화 조회 API",
            description = "나라별 회화 전체 조회"
    )
    @GetMapping("")
    public RestApiResponse<List<GetConversationListResponse>> getConversationList(
            @RequestParam Long countryId,
            @RequestParam("cate") ConversationCate cate) {

        return RestApiResponse.success(
                conversationService.getConversationList(countryId, cate));
    }


}
