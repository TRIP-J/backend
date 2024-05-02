package com.tripj.domain.board.model.dto.request;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.user.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardRequest {

    @Nullable
    private Long lastBoardId;

    public static GetBoardRequest of(Long lastBoardId) {
        return new GetBoardRequest(lastBoardId);
    }

}
