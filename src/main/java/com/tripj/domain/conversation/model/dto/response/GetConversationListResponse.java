package com.tripj.domain.conversation.model.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.country.model.entity.Country;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetConversationListResponse {

    @Schema(description = "회화 Id", example = "1")
    private Long id;

    @Schema(description = "나라 Id", example = "1")
    private Long countryId;

    @Schema(description = "회화 카테고리 Id", example = "BASIC")
    private String cate;

    @Schema(description = "회화 뜻", example = "안녕하세요")
    private String mean;

    @Schema(description = "외국어", example = "你好")
    private String original;

    @Schema(description = "알파벳", example = "ni hao")
    private String alphabet;

    public static GetConversationListResponse of(
            Long id, Long countryId, String cate,
            String mean, String original, String alphabet) {
        return new GetConversationListResponse(
                id, countryId, cate, mean, original, alphabet);
    }

}
