package com.tripj.domain.item.repository;

import com.tripj.domain.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {

    @Query("select i from Item i join Trip t where t.endDate = CURRENT DATE and i.previous = 'NOW'")
    List<Item> findAllPreviousIsNow();

    @Query("select max(i.previous) from Item i where i.trip.id = :tripId and i.previous = '%B'")
    String findMaxPrevious(@Param("tripId") Long tripId);

    @Query("select i from Item i where i.id = :itemId and ((i.previous = 'NOW' and i.fix is null) or (i.previous is null and i.fix is not null))")
    Optional<Item> findByPreviousIsNowOrFixIsF(@Param("itemId") Long itemId);
}
