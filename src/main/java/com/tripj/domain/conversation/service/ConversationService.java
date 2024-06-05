package com.tripj.domain.conversation.service;

import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.conversation.model.dto.response.GetConversationListResponse;
import com.tripj.domain.conversation.model.entity.Conversation;
import com.tripj.domain.conversation.repository.ConversationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ConversationService {

    private final ConversationRepository conversationRepository;

    /**
     * 나라별 회화 조회
     */
    public List<GetConversationListResponse> getConversationList(Long countryId, ConversationCate cate) {

        List<Conversation> results = conversationRepository.findByCountryIdAndCate(countryId, cate);

        List<GetConversationListResponse> responseList = results.stream()
                .map(conversation -> GetConversationListResponse.of(
                        conversation.getId(), conversation.getCountry().getId(), conversation.getCate().getValue(),
                        conversation.getMean(), conversation.getOriginal(), conversation.getAlphabet()))
                .collect(Collectors.toList());

        return responseList;
    }
}
