package com.tripj.domain.board.service;

import com.tripj.domain.board.model.dto.CreateBoardRequest;
import com.tripj.domain.board.model.dto.CreateBoardResponse;
import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.like.repository.LikedBoardRepository;
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
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCateRepository boardCateRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikedBoardRepository likedBoardRepository;

    /**
     * 게시글 등록
     */
    public CreateBoardResponse createBoard(CreateBoardRequest request,
                                           Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        BoardCate boardCate = boardCateRepository.findById(request.getBoardCateId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD_CATE));

        Board savedBoard = boardRepository.save(request.toEntity(user, boardCate));

        return CreateBoardResponse.of(savedBoard.getId());
    }

    /**
     * 게시글 수정
     */
    public CreateBoardResponse updateBoard(
            CreateBoardRequest request, Long boardId, Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        BoardCate boardCate = boardCateRepository.findById(request.getBoardCateId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD_CATE));

        if (board.getUser().getId().equals(userId)) {
            board.updateBoard(request.getTitle(), request.getContent());
        } else {
            // throws
            throw new ForbiddenException("자신의 게시물만 수정 가능합니다.", ErrorCode.E403_FORBIDDEN);
        }

        return CreateBoardResponse.of(board.getId());
    }

    /**
     * 게시물 삭제
     */
    public CreateBoardResponse deleteBoard(Long userId, Long boardId) {

        Board board = boardRepository.findById(boardId)
           .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        if (board.getUser().getId().equals(userId)) {
            commentRepository.deleteByBoardId(boardId);
            likedBoardRepository.deleteByBoardId(boardId);
            boardRepository.deleteById(board.getId());
        } else {
            throw new ForbiddenException("자신의 게시물만 삭제 가능합니다.", ErrorCode.E403_FORBIDDEN);
        }

        return CreateBoardResponse.of(board.getId());
    }
}
