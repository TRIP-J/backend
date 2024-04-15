package com.tripj.domain.trip.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.country.model.entity.Country;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Builder
@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Trip extends BaseTimeEntity {

    @Id
    @Column(name = "trip_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private LocalDate startDate;

    private LocalDate endDate;

    private String tripName;

    private String purpose;

    private String previous;

    public static Trip newTrip(String tripName, String purpose, String previous,
                               LocalDate startDate, LocalDate endDate,
                               User user, Country country) {
        return Trip.builder()
                .tripName(tripName)
                .purpose(purpose)
                .previous("NOW")
                .startDate(startDate)
                .endDate(endDate)
                .user(user)
                .country(country)
                .build();
    }

    /**
     * 여행 수정
     */
    public void updateTrip(String tripName, String purpose,
                           LocalDate startDate, LocalDate endDate,
                           Country country) {
        this.tripName = tripName;
        this.purpose = purpose;
        this.startDate = startDate;
        this.endDate = endDate;
        this.country = country;
    }





}
