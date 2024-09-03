package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.entity.CheckList;
import com.tripj.domain.item.constant.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, Long>, CheckListRepositoryCustom {

//    Optional<PackCheckListResponse> findByItemIdAndChecklistIdAndPack(Long itemId, Long checkListId, String pack);
//    Optional<PackCheckListResponse> findByItemIdAndId(Long itemId, Long id);
//    @Query("select cl from CheckList cl where cl.user.id = :userId and cl.item.id = :itemId and cl.trip.id = :tripId  and cl.previous = 'NOW'")
//    Optional<CheckList> findByUserIdAndItemIdAndTripIdAndPreviousNow(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("tripId") Long tripId);

//    @Query("select cl from CheckList cl join Trip i where i.endDate = CURRENT DATE and cl.previous = 'NOW'")
//    List<CheckList> findAllPreviousIsNow();

//    @Query("select max(cl.previous) from CheckList cl where cl.trip.id = :tripId and cl.previous like '%B'")
//    String findMaxPrevious(@Param("tripId") Long tripId);

    @Query("select cl from CheckList cl join Trip t on cl.trip.id = t.id " +
            "where cl.user.id = :userId " +
            "and (cl.item.id = :itemId or cl.fixedItem.id = :itemId) " +
            "and cl.trip.id = :tripId " +
            "and t.previous = 'NOW' " +
            "and cl.itemType = :itemType")
    Optional<CheckList> findCheckListByUserItemAndCurrentTrip(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("tripId") Long tripId, @Param("itemType")ItemType itemType);

}
