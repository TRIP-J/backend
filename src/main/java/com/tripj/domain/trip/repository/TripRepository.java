package com.tripj.domain.trip.repository;

import com.tripj.domain.trip.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    Trip findByUserId(Long userId);

    @Query("select t from Trip t where t.endDate = CURRENT_DATE and t.previous = 'NOW'")
    List<Trip> findAllPreviousIsNow();

    @Query("select max(t.previous) from Trip t where t.user.id = :userId and t.previous like 'B%'")
    String findMaxPrevious(@Param("userId") Long userId);

    @Query("select t from Trip t where t.previous = 'NOW' and t.id = :tripId")
    Optional<Trip> findByPreviousIsNow(@Param("tripId") Long tripId);
}
