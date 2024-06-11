package com.tripj.domain.boardimg.repository;

import com.tripj.domain.boardimg.model.entity.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {
}
