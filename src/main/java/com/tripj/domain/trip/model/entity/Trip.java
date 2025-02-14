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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "country_id")
    private Country country;

    private LocalDate startDate;

    private LocalDate endDate;

    private String tripName;

    private String purpose;

    private String previous;

    public static Trip newTrip(LocalDate startDate, LocalDate endDate,
                               User user, Country country) {
        return Trip.builder()
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
    public void updateTrip(LocalDate startDate, LocalDate endDate, Country country) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.country = country;
    }

    /**
     * 스케줄러로 Previous 변경
     */
    public void updatePrevious(String previous) {
        this.previous = previous;
    }

}
