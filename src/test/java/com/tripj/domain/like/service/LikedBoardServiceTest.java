package com.tripj.domain.like.service;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.response.CreateBoardResponse;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.board.service.BoardService;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.comment.service.CommentService;
import com.tripj.domain.like.model.dto.request.CreateLikedBoardRequest;
import com.tripj.domain.like.model.dto.response.CreateLikedBoardResponse;
import com.tripj.domain.like.repository.LikedBoardRepository;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.code.ErrorCode;
import com.tripj.global.error.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class LikedBoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private LikedBoardService likedBoardService;
    @Autowired
    private LikedBoardRepository likedBoardRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BoardCateRepository boardCateRepository;

    private User user;
    private BoardCate boardCate;

    @BeforeEach
    void setUp() {

        user = User.builder()
                .userType(UserType.KAKAO)
                .email("asdf@naver.com")
                .nickname("다람지기엽지")
                .userName("홍길동")
                .role(Role.ROLE_USER)
                .build();
        userRepository.save(user);

        boardCate = BoardCate.builder()
                .boardCateCode("REV")
                .boardCateName("후기")
                .build();
        boardCateRepository.save(boardCate);
    }

    @AfterEach
    void tearDown() {
        likedBoardRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        boardCateRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Nested
    class createLikeBoard {

        @Test
        @DisplayName("게시글 좋아요 누르기에 성공합니다")
        void createLikeBoard() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse response = boardService.createBoard(request, user.getId(), null);

            //when
            CreateLikedBoardResponse likedBoard =
                    likedBoardService.createLikedBoard(new CreateLikedBoardRequest(response.getBoardId()), user.getId());

            //then
            assertThat(likedBoard).isNotNull();
            assertThat(likedBoard.getBoardId()).isEqualTo(likedBoard.getBoardId());
        }

        @Test
        @DisplayName("존재하지 않는 유저가 게시글 좋아요 누를시 예외가 발생합니다.")
        void createLikeBoardNotExistingUser() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse response = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> likedBoardService.createLikedBoard(new CreateLikedBoardRequest(response.getBoardId()), 99L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 게시글에 좋아요 누를시 예외가 발생합니다.")
        void createLikeBoardNotExistingBoard() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse response = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> likedBoardService.createLikedBoard(new CreateLikedBoardRequest(2L), user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(ErrorCode.E404_NOT_EXISTS_BOARD.getMessage());
        }
    }

    private CreateBoardRequest createBoardRequest(String title, String content, Long boardCateId) {
        return CreateBoardRequest.builder()
                .boardCateId(boardCateId)
                .title(title)
                .content(content)
                .build();
    }


}