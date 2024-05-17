package com.tripj.domain.conversation.model.entity;

import com.tripj.domain.conversation.model.constant.ConversationCate;
import com.tripj.domain.country.model.entity.Country;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Country country;

    @Enumerated(EnumType.STRING)
    private ConversationCate cate;

    private String mean;

    private String original;

    private String alphabet;

}
