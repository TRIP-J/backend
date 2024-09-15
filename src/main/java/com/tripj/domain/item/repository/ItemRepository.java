package com.tripj.domain.item.repository;

import com.tripj.domain.item.model.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom {

    void deleteByUserId(Long id);

    @Modifying
    @Query("DELETE FROM Item i WHERE i.trip.id = :tripId AND i.itemCate.id = :itemCateId")
    void deleteByTripIdAndItemCateId(Long tripId, Long itemCateId);

//    @Query("select i from Item i left join Trip t on i.trip.id = t.id " +
//            "where i.id = :itemId " +
//            "and ((i.user.id = :userId and i.fix = 'N' and t.previous = 'NOW') " +
//            "or (i.fix = 'F') )")
//    Optional<Item> findItemsByUserAndPreviousIsNow(@Param("itemId") Long itemId, @Param("userId") Long userId);

//    @Query("select max(i.previous) from Item i where i.trip.id = :tripId and i.previous = '%B'")
//    String findMaxPrevious(@Param("tripId") Long tripId);

//    @Query("select i from Item i where i.id = :itemId and ((i.previous = 'NOW' and i.fix = 'N') or (i.previous is null and i.fix = 'F'))")
//    Optional<Item> findByPreviousIsNowOrFixIsF(@Param("itemId") Long itemId);

//    @Query("select i from Item i where i.id = :itemId and ((i.user.id = :userId and i.fix = 'N') or (i.fix = 'F'))")
//    Item findByPreviousIsNowOrFixIsF2(@Param("itemId") Long itemId, @Param("userId") Long userId);

//    @Query("select i from Item i join Trip t where t.endDate = CURRENT DATE and i.previous = 'NOW'")
//    List<Item> findAllPreviousIsNow();
}
