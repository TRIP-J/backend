package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.dto.PackCheckListResponse;
import com.tripj.domain.checklist.model.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long>, CheckListRepositoryCustom {

//    Optional<PackCheckListResponse> findByItemIdAndChecklistIdAndPack(Long itemId, Long checkListId, String pack);
    Optional<PackCheckListResponse> findByItemIdAndId(Long itemId, Long id);

    @Query("select cl from CheckList cl where cl.user.id = :userId and cl.item.id = :itemId and cl.trip.id = :tripId  and cl.previous = 'NOW'")
    Optional<CheckList> findByUserIdAndItemIdAndTripIdAndPreviousNow(@Param("userId") Long userId, @Param("itemId") Long itemId, @Param("tripId") Long tripId);


}
