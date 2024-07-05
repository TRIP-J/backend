package com.tripj.domain.item.model.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateItemRequest {

    @Schema(description = "아이템명", example = "고데기")
    @NotNull(message = "아이템명은 필수로 입력해 주세요.")
    private String itemName;

}
