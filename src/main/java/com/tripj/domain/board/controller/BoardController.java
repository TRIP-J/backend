package com.tripj.domain.board.controller;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.request.GetBoardRequest;
import com.tripj.domain.board.model.dto.response.CreateBoardResponse;
import com.tripj.domain.board.model.dto.response.GetBoardCommentResponse;
import com.tripj.domain.board.model.dto.response.GetBoardResponse;
import com.tripj.domain.board.service.BoardService;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/board")
@Tag(name = "board", description = "게시글 API")
public class BoardController {

    private final BoardService boardService;

    @Operation(
            summary = "게시글 등록 API",
            description = "게시글을 등록합니다."
    )
    @PostMapping("")
    //TODO : S3에 첨부파일 올리기
    public RestApiResponse<CreateBoardResponse> createBoard(
            @Validated @RequestPart CreateBoardRequest request,
            Long userId,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return RestApiResponse.error(ErrorCode.E401_BINDING_RESULT, errorMessage);
        }

        return RestApiResponse.success(
                boardService.createBoard(request, userId));
    }

    @Operation(
            summary = "게시글 수정 API",
            description = "게시글을 수정합니다."
    )
    @PostMapping("/{boardId}")
    public RestApiResponse<CreateBoardResponse> updateBoard(
            @Validated @RequestPart CreateBoardRequest request,
            @PathVariable Long boardId, Long userId,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return RestApiResponse.error(ErrorCode.E401_BINDING_RESULT, errorMessage);
        }

        return RestApiResponse.success(
                boardService.updateBoard(request, boardId, userId));
    }

    @Operation(
            summary = "게시글 삭제 API",
            description = "게시글을 삭제합니다."
    )
    @DeleteMapping("/{boardId}")
    public RestApiResponse<CreateBoardResponse> deleteBoard(
            Long userId, @PathVariable Long boardId) {

        return RestApiResponse.success(
                boardService.deleteBoard(userId, boardId));
    }

    @Operation(
            summary = "게시글 상세 조회 API",
            description = "게시글을 상세조회 합니다."
    )
    @GetMapping("/{boardId}")
    public RestApiResponse<GetBoardResponse> getBoard(
            @PathVariable Long boardId) {

        return RestApiResponse.success(
                boardService.getBoard(boardId));
    }

    @Operation(
            summary = "게시글 상세 댓글 조회 API",
            description = "게시글 상세조회시 댓글을 조회 합니다."
    )
    @GetMapping("/{boardId}/comments")
    public RestApiResponse<GetBoardCommentResponse> getBoardComment(
            @PathVariable Long boardId) {

        return RestApiResponse.success(
                boardService.getBoardComment(boardId));
    }

    @Operation(
            summary = "게시글 리스트 조회 API",
            description = "게시글(후기,질문,꿀팁) 리스트 조회 무한스크롤 합니다."
    )
    @GetMapping("/scroll")
    public RestApiResponse<Slice<GetBoardResponse>> getBoardListScroll(
            @RequestParam(required = false) Long lastBoardId,
            @PageableDefault(size = 5) Pageable pageable) {

        GetBoardRequest request = GetBoardRequest.of(lastBoardId);
        Slice<GetBoardResponse> getBoardList =
                boardService.getBoardListScroll(request, pageable);

        return RestApiResponse.success(getBoardList);
    }

    @Operation(
            summary = "게시글 리스트 조회 API",
            description = "게시글(후기,질문,꿀팁) 리스트 조회 합니다."
    )
    @GetMapping("")
    public RestApiResponse<List<GetBoardResponse>> getBoardList(
            @RequestParam Long boardCateId) {

        return RestApiResponse.success(boardService.getBoardList(boardCateId));
    }

    @Operation(
            summary = "최신글 리스트 조회 API",
            description = "최신글 리스트 조회 합니다."
    )
    @GetMapping("/latest")
    public RestApiResponse<List<GetBoardResponse>> getBoardLatestList() {

        return RestApiResponse.success(boardService.getBoardLatestList());
    }

    @Operation(
            summary = "인기글 리스트 조회 API",
            description = "인기글 리스트 조회 합니다."
    )
    @GetMapping("/popular")
    public RestApiResponse<List<GetBoardResponse>> getBoardPopularList() {

        return RestApiResponse.success(boardService.getBoardPopularList());
    }




















}
