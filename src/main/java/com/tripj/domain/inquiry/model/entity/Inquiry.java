package com.tripj.domain.inquiry.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inquiry extends BaseTimeEntity {

    @Id
    @Column(name = "inquiry_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String content;

    public static Inquiry newInquiry(String content, User user) {
        return Inquiry.builder()
                .content(content)
                .user(user)
                .build();
    }
}
