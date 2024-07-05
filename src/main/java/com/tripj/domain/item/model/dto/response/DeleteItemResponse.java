package com.tripj.domain.item.model.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DeleteItemResponse {

    @Schema(description = "아이템 Id", example = "1")
    private Long itemId;

    public static DeleteItemResponse of(Long itemId) {
        return new DeleteItemResponse(itemId);
    }
}
