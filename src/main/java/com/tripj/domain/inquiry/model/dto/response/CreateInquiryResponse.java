package com.tripj.domain.inquiry.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Schema(description = "문의사항 보내기 Response DTO")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateInquiryResponse {

    @Schema(description = "문의하기 ID", example = "1")
    private Long inquiryId;

    public static CreateInquiryResponse of(Long inquiryId) {
        return new CreateInquiryResponse(inquiryId);
    }
}
