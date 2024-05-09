package com.tripj.domain.item.repository;

import com.tripj.domain.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join Trip t where t.endDate = CURRENT DATE and i.previous = 'NOW'")
    List<Item> findAllPreviousIsNow();

    @Query("select max(i.previous) from Item i where i.trip.id = :tripId and i.previous = '%B'")
    String findMaxPrevious(@Param("tripId") Long tripId);
}
