package com.tripj.domain.item.repository;

import com.tripj.domain.item.model.entity.FixedItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedItemRepository extends JpaRepository<FixedItem, Long> {


}
