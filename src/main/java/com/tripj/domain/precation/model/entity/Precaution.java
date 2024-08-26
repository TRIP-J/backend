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

    @Lob
    @Column(columnDefinition = "TEXT")
    private String contact;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String traffic;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String culture;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String accident;

    public static Precaution newPrecaution(String contact, String traffic, String culture, String accident) {
        return Precaution.builder()
                .contact(contact)
                .traffic(traffic)
                .culture(culture)
                .accident(accident)
                .build();
    }
}
