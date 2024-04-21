package com.tripj.domain.board.controller;

import com.tripj.domain.board.model.dto.CreateBoardRequest;
import com.tripj.domain.board.model.dto.CreateBoardResponse;
import com.tripj.domain.board.service.BoardService;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

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
    public RestApiResponse<CreateBoardResponse> createBoard(
            @ModelAttribute @Validated CreateBoardRequest request,
            Long userId,
            BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            return RestApiResponse.error(ErrorCode.E401_BINDING_RESULT, errorMessage);
        }

        return RestApiResponse.success(
                boardService.createBoard(request, userId));
    }


}
