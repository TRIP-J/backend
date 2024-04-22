package com.tripj.domain.like.controller;

import com.tripj.domain.like.model.dto.CreateLikedBoardRequest;
import com.tripj.domain.like.model.dto.CreateLikedBoardResponse;
import com.tripj.domain.like.service.LikedBoardService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/like/board")
@Tag(name = "likedBoard", description = "게시판 좋아요 API")
public class LikedBoardController {

    private final LikedBoardService boardService;

    @Operation(
            summary = "게시판 좋아요 API",
            description = "게시판에 좋아요를 누릅니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateLikedBoardResponse> createLikedBoard(@RequestBody CreateLikedBoardRequest request,
                                                                      Long userId) {

        return RestApiResponse.success(
                boardService.createLikedBoard(request, userId));
    }




}
