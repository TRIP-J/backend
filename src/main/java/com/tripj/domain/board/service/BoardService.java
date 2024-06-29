package com.tripj.domain.board.service;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.request.GetBoardRequest;
import com.tripj.domain.board.model.dto.request.GetBoardSearchRequest;
import com.tripj.domain.board.model.dto.response.*;
import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.boardimg.model.entity.BoardImg;
import com.tripj.domain.boardimg.service.BoardImgService;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.like.repository.LikedBoardRepository;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardCateRepository boardCateRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikedBoardRepository likedBoardRepository;
    private final BoardImgService boardImgService;

    /**
     * 게시글 등록
     */
    public CreateBoardResponse createBoard(
            CreateBoardRequest request, Long userId, List<MultipartFile> images) throws IOException {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        BoardCate boardCate = boardCateRepository.findById(request.getBoardCateId())
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD_CATE));

        Board savedBoard = boardRepository.save(request.toEntity(user, boardCate));

        if (images != null) {
            boardImgService.validateImgCount(images, 5L);
            boardImgService.uploadBoardImg(savedBoard, images);
        }

        return CreateBoardResponse.of(savedBoard);
    }

    /**
     * 게시글 수정
     */
    public CreateBoardResponse updateBoard(
            CreateBoardRequest request, Long boardId, Long userId,
            List<MultipartFile> images) throws IOException {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_USER));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        BoardCate boardCate = boardCateRepository.findById(request.getBoardCateId())
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD_CATE));

        if (board.getUser().getId().equals(userId)) {
            board.updateBoard(request.getTitle(), request.getContent());
        } else {
            throw new ForbiddenException(ErrorCode.E403_NOT_MY_BOARD);
        }

        if (images != null) {
            boardImgService.validateImgCount(images, 5L);
        }
        boardImgService.updateImg(board, images);

        return CreateBoardResponse.of(board);
    }

    /**
     * 게시물 삭제
     */
    public DeleteBoardResponse deleteBoard(
            Long userId, Long boardId) {

        Board board = boardRepository.findById(boardId)
           .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        if (board.getUser().getId().equals(userId)) {
            boardImgService.deleteImagesInS3(board);
            boardRepository.deleteById(board.getId());
        } else {
            throw new ForbiddenException(ErrorCode.E403_NOT_MY_BOARD);
        }

        return DeleteBoardResponse.of(board);
    }

    /**
     * 게시글 상세조회
     */
    @Transactional(readOnly = true)
    public GetBoardDetailResponse getBoardDetail(Long boardId) {

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        GetBoardDetailResponse boardDetail = boardRepository.getBoardDetail(boardId);

        List<String> imgUrlList = board.getBoardImg().stream()
                 .map(boardImg -> boardImg.getUrl())
                 .collect(Collectors.toList());

        if (boardDetail.getImgUrl() == null) {
            boardDetail.setImgUrl(imgUrlList);
        }

        return boardDetail;
    }


    /**
     * 게시글 댓글 조회
     */
    public List<GetBoardCommentResponse> getBoardComment(Long boardId) {

        Board board = boardRepository.findById(boardId)
            .orElseThrow(() -> new NotFoundException(ErrorCode.E404_NOT_EXISTS_BOARD));

        return commentRepository.findByBoardId(boardId);
    }

    /**
     * 게시글 리스트 조회 (무한스크롤)
     */
    @Transactional(readOnly = true)
    public Slice<GetBoardResponse> getBoardListScroll(GetBoardRequest request, Pageable pageable) {

        return boardRepository.findAllPaging(request.getLastBoardId(), pageable)
                .map(board -> GetBoardResponse.of(
                        board.getUserId(),
                        board.getUserName(),
                        board.getProfile(),
                        board.getBoardId(),
                        board.getTitle(),
                        board.getContent(),
                        board.getBoardCateName(),
                        board.getRegTime(),
                        board.getCommentCnt(),
                        board.getLikeCnt()));
    }

    /**
     * 게시글 전체 리스트 조회
     */
    public List<GetBoardResponse> getBoardList(Long boardCateId) {
        return boardRepository.getBoardList(boardCateId);
    }

    /**
     * 최신글 전체 리스트 조회
     */
    public List<GetBoardResponse> getBoardLatestList() {
        return boardRepository.getBoardLatestList();
    }

    /**
     * 인기글 전체 리스트 조회
     */
    public List<GetBoardResponse> getBoardPopularList() {
        return boardRepository.getBoardPopularList();
    }

    /**
     * 게시글 전체 검색 조회
     */
    public List<GetBoardResponse> getAllBoardList(GetBoardSearchRequest request) {
        return boardRepository.getAllBoardList(request);
    }

    /**
     * 내 게시글 조회
     */
    public List<GetBoardResponse> getMyBoardList(Long userId) {
        return boardRepository.getMyBoardList(userId);
    }

    /**
     * 내 좋아요
     */
    public List<GetBoardResponse> getMyLikedBoard(Long userId) {
        return boardRepository.getMyLikedBoard(userId);
    }

}
