package com.tripj.domain.trip.repository;

import com.tripj.domain.trip.model.entity.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {

}
