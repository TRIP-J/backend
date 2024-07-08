package com.tripj.domain.board.model.dto.request;

import jakarta.annotation.Nullable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetBoardSearchRequest {

    private String keyword; //검색어
}
