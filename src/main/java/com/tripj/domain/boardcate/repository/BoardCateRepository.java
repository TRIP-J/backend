package com.tripj.domain.boardcate.repository;

import com.tripj.domain.boardcate.model.entity.BoardCate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCateRepository extends JpaRepository<BoardCate, Long> {
}
