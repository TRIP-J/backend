package com.tripj.domain.trip.repository;

import com.tripj.domain.trip.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long>, TripRepositoryCustom {

    List<Trip> findByUserId(Long userId);

    @Query("select t from Trip t where t.endDate = CURRENT_DATE - 1 and t.previous = 'NOW'")
    List<Trip> findAllPreviousIsNow();

    @Query("select t from Trip t where t.previous = 'NOW' and t.user.id = :userId")
    Optional<Trip> findTripPreviousIsNow(@Param("userId") Long userId);

    @Query("select t from Trip t where t.previous != 'NOW' and t.user.id = :userId")
    List<Trip> findTripPreviousIsNotNow(@Param("userId") Long userId);

    @Query("select max(t.previous) from Trip t where t.user.id = :userId and t.previous like 'B%'")
    String findMaxPrevious(@Param("userId") Long userId);

    @Query("select t from Trip t where t.previous = 'NOW' and t.id = :tripId")
    Optional<Trip> findByPreviousIsNow(@Param("tripId") Long tripId);

    Long countByUserId(Long userId);

    void deleteByUserId(Long id);

}
