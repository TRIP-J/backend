package com.tripj.domain.board.model.dto.request;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardSearchRequest {

    private String title;
    private String content;
}
