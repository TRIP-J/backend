package com.tripj.domain.checklist.repository;

import com.tripj.domain.checklist.model.entity.CheckList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckListRepository extends JpaRepository<CheckList, Long>, CheckListRepositoryCustom {


}
