package com.tripj.domain.conversation.model.dto.response;

import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.country.model.entity.Country;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetConversationListResponse {

    private Long id;
    private Country country;
    private ConversationCate cate;
    private String mean;
    private String original;
    private String alphabet;

    public static GetConversationListResponse of(
            Long id, Country country, ConversationCate cate,
            String mean, String original, String alphabet) {
        return new GetConversationListResponse(
                id, country, cate, mean, original, alphabet);
    }

}
