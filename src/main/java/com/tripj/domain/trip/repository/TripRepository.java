package com.tripj.domain.trip.repository;

import com.tripj.domain.trip.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    Trip findByUserId(Long userId);

    @Query("select t from Trip t where t.endDate = CURRENT_DATE and t.previous = 'NOW'")
    List<Trip> findAllPreviousIsNow();
}
