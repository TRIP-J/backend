package com.tripj.domain.like.service;

import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.like.model.dto.CreateLikedBoardRequest;
import com.tripj.domain.like.model.dto.CreateLikedBoardResponse;
import com.tripj.domain.like.model.entity.LikedBoard;
import com.tripj.domain.like.repository.LikedBoardRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class LikedBoardService {

    private final UserRepository userRepository;
    private final BoardRepository boardRepository;
    private final LikedBoardRepository likedBoardRepository;

    public CreateLikedBoardResponse createLikedBoard(CreateLikedBoardRequest request,
                                                     Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Board board = boardRepository.findById(request.getBoardId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        //좋아요 누르기
        boolean addLike = addLike(userId, request.getBoardId());

        if (addLike == true) {
            LikedBoard likedBoard = likedBoardRepository.save(request.toEntity(user, board));
            return CreateLikedBoardResponse.of(
                    likedBoard.getBoard().getId(), likedBoard.getId());
        } else {
            likedBoardRepository.deleteByUserIdAndBoardId(userId, request.getBoardId());
            return CreateLikedBoardResponse.of(null,null);
        }
    }

    /**
     * 좋아요 누르기
     */
    public boolean addLike(Long userId, Long boardId) {
        //이미 누른 좋아요인지 확인
        if (validateLikedDaily(userId, boardId)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 좋아요 중복검사
     */
    public boolean validateLikedDaily(Long userId, Long boardId) {
        return likedBoardRepository.findByUserIdAndBoardId(userId, boardId).isEmpty();
    }


}
