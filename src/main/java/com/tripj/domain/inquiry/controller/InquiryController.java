package com.tripj.domain.inquiry.controller;

import com.tripj.domain.inquiry.model.dto.response.CreateInquiryRequest;
import com.tripj.domain.inquiry.model.dto.response.CreateInquiryResponse;
import com.tripj.domain.inquiry.service.InquiryService;
import com.tripj.global.model.RestApiResponse;
import com.tripj.resolver.userinfo.UserInfo;
import com.tripj.resolver.userinfo.UserInfoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inquiry")
@Tag(name = "inquiry", description = "문의사항 API")
public class InquiryController {

    private final InquiryService inquiryService;

    @Operation(
            summary = "문의 사항 등록 API",
            description = "문의 사항 등록시 DB에 저장하고, 메일로 전송합니다."
    )
    @PostMapping("")
    RestApiResponse<CreateInquiryResponse> createInquiry(
            @UserInfo UserInfoDto userInfo,
            @RequestBody @Valid CreateInquiryRequest request) {

        return RestApiResponse.success(
                inquiryService.createInquiry(userInfo.getUserId(), request));
    }

}
