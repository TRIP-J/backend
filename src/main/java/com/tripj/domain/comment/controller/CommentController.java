package com.tripj.domain.comment.controller;

import com.tripj.domain.comment.model.dto.CreateCommentRequest;
import com.tripj.domain.comment.model.dto.CreateCommentResponse;
import com.tripj.domain.comment.service.CommentService;
import com.tripj.global.model.RestApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Tag(name = "comment", description = "댓글 API")
public class CommentController {

    private final CommentService commentService;

    @Operation(
            summary = "게시글에 댓글 등록 API",
            description = "게시글에 댓글을 등록합니다."
    )
    @PostMapping("")
    public RestApiResponse<CreateCommentResponse> createComment(@RequestBody CreateCommentRequest request,
                                                                Long userId) {

        return RestApiResponse.success(commentService.createComment(request, userId));
    }

    @Operation(
            summary = "게시글에 댓글 수정 API",
            description = "게시글에 댓글을 수정합니다."
    )
    @PostMapping("/{commentId}")
    public RestApiResponse<CreateCommentResponse> updateComment(@RequestBody CreateCommentRequest request,
                                                                @PathVariable Long commentId,
                                                                Long userId) {

        return RestApiResponse.success(commentService.updateComment(request, commentId, userId));
    }


}
