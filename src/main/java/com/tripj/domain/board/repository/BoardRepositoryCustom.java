package com.tripj.domain.board.repository;

import com.tripj.domain.board.model.dto.response.GetBoardResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface BoardRepositoryCustom {
    Slice<GetBoardResponse> findAllPaging(Long lastBoardId, Pageable pageable);

    GetBoardResponse getBoardDetail(Long boardId);




}
