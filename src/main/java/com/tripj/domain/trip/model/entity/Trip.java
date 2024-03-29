package com.tripj.domain.trip.model.entity;

import com.tripj.domain.common.entity.BaseTimeEntity;
import com.tripj.domain.user.model.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class Trip extends BaseTimeEntity {

    @Id
    @Column(name = "trip_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Embedded
    private Period TripPeriod;

    private String tripName;

    private String purpose;

    private String previous;
}
