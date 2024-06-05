package com.tripj.domain.conversation.repository;

import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.conversation.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {

     Optional<Conversation> findByCountryId(Long countryId);

     @Query("select c from Conversation c where c.country.id = :countryId and c.cate = :cate")
     List<Conversation> findByCountryIdAndCate(@Param("countryId") Long countryId, @Param("cate") ConversationCate cate);

}
