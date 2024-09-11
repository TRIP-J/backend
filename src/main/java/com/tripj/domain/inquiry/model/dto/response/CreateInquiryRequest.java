package com.tripj.domain.inquiry.model.dto.response;

import com.tripj.domain.inquiry.model.entity.Inquiry;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateInquiryRequest {

    @Size(min = 1, max = 500, message = "내용은 최소 1자에서 최대 500자까지 가능합니다.")
    @Schema(description = "문의사항 내용", example = "문의사항 예시내용 입니다.")
    private String content;

    public Inquiry toEntity(String content, User user) {
        return Inquiry.newInquiry(content, user);
    }

}
