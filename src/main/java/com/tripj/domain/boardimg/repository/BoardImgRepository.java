package com.tripj.domain.boardimg.repository;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardimg.model.entity.BoardImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardImgRepository extends JpaRepository<BoardImg, Long> {

    List<BoardImg> findAllByBoard(Board board);
}
