package com.tripj.domain.board.service;

import com.tripj.domain.board.model.dto.CreateBoardRequest;
import com.tripj.domain.board.model.dto.CreateBoardResponse;
import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCateRepository boardCateRepository;
    private final UserRepository userRepository;

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







}
