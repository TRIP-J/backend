package com.tripj.domain.comment.service;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.comment.model.dto.request.CreateCommentRequest;
import com.tripj.domain.comment.model.dto.response.CreateCommentResponse;
import com.tripj.domain.comment.model.dto.response.DeleteCommentResponse;
import com.tripj.domain.comment.model.entity.Comment;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    /**
     * 댓글 등록
     */
    public CreateCommentResponse createComment(CreateCommentRequest request,
                                               Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Board board = boardRepository.findById(request.getBoardId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        Comment savedComment = commentRepository.save(request.toEntity(user, board));

        return CreateCommentResponse.of(savedComment.getId(),
                                        savedComment.getBoard().getId());
    }

    /**
     * 댓글 수정
     */
    public CreateCommentResponse updateComment(CreateCommentRequest request,
                                               Long commentId,
                                               Long userId) {

        userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Board board = boardRepository.findById(request.getBoardId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COMMENT));

        if (board.getUser().getId().equals(userId)) {
            comment.updateComment(request.getContent());
        } else {
            throw new ForbiddenException("자신의 댓글만 수정 가능합니다", ErrorCode.E403_FORBIDDEN);
        }

        return CreateCommentResponse.of(comment.getId(),
                                        board.getId());
    }

    /**
     * 게시글에 댓글 삭제
     */
    public DeleteCommentResponse deleteComment(Long commentId, Long userId) {

        Comment comment = commentRepository.findById(commentId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_COMMENT));

        if (comment.getUser().getId().equals(userId)) {
            commentRepository.deleteById(commentId);
        } else {
            throw new ForbiddenException("자신의 댓글만 삭제 가능합니다", ErrorCode.E403_FORBIDDEN);
        }

        return DeleteCommentResponse.of(comment.getId(),
                                        comment.getBoard().getId());
    }
}
