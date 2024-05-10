package com.tripj.domain.trip.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.country.model.entity.QCountry;
import com.tripj.domain.trip.model.dto.GetTripResponse;
import com.tripj.domain.trip.model.dto.QGetTripResponse;
import com.tripj.domain.trip.model.entity.QTrip;
import com.tripj.domain.trip.model.entity.Trip;

import java.util.List;

import static com.tripj.domain.country.model.entity.QCountry.*;
import static com.tripj.domain.trip.model.entity.QTrip.trip;

public class TripRepositoryCustomImpl implements TripRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TripRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public GetTripResponse getTrip(Long userId) {
        GetTripResponse results = queryFactory
                .select(new QGetTripResponse(
                        trip.id,
                        trip.user.id,
                        country.name,
                        trip.tripName,
                        trip.purpose,
                        trip.startDate,
                        trip.endDate
                ))
                .from(trip)
                .join(trip.country, country)
                .where(
                        trip.previous.eq("NOW"),
                        trip.user.id.eq(userId)
                )
                .fetchOne();

        return results;
    }

    @Override
    public List<GetTripResponse> getPastTrip(Long userId) {
        List<GetTripResponse> result = queryFactory
                .select(new QGetTripResponse(
                        trip.id,
                        trip.user.id,
                        trip.country.name,
                        trip.tripName,
                        trip.purpose,
                        trip.startDate,
                        trip.endDate
                ))
                .from(trip)
                .join(trip.country, country)
                .where(
                        trip.previous.ne("NOW"),
                        trip.user.id.eq(userId)
                )
                .orderBy(trip.startDate.desc())
                .fetch();

        return result;
    }
}
