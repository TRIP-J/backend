package com.tripj.domain.boardimg.repository;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardimg.model.entity.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {

    List<BoardImg> findAllByBoard(Board board);
}
