package com.tripj.domain.itemcate.repository;

import com.tripj.domain.itemcate.model.entity.ItemCate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCateRepository extends JpaRepository<ItemCate, Long> {
}
