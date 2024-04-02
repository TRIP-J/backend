package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.dto.PackCheckListResponse;
import com.tripj.domain.checklist.model.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CheckListRepository extends JpaRepository<CheckList, Long>, CheckListRepositoryCustom {

//    Optional<PackCheckListResponse> findByItemIdAndChecklistIdAndPack(Long itemId, Long checkListId, String pack);
    Optional<PackCheckListResponse> findByItemIdAndId(Long itemId, Long id);


}
