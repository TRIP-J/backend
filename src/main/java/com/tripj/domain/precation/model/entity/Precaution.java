package com.tripj.domain.precation.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Precaution extends BaseTimeEntity {

    @Id
    @Column(name = "precaution_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private String title;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    public static Precaution newPrecaution(String title, String content) {
        return Precaution.builder()
                .title(title)
                .content(content)
                .build();
    }
}
