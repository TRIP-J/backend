package com.tripj.domain.board.service;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.response.CreateBoardResponse;
import com.tripj.domain.board.model.dto.response.DeleteBoardResponse;
import com.tripj.domain.board.model.dto.response.GetBoardCommentResponse;
import com.tripj.domain.board.model.dto.response.GetBoardDetailResponse;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.comment.model.dto.request.CreateCommentRequest;
import com.tripj.domain.comment.model.dto.response.CreateCommentResponse;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.comment.service.CommentService;
import com.tripj.domain.country.repository.CountryRepository;
import com.tripj.domain.trip.repository.TripRepository;
import com.tripj.domain.trip.service.TripService;
import com.tripj.domain.user.constant.Role;
import com.tripj.domain.user.constant.UserType;
import com.tripj.domain.user.model.entity.User;
import com.tripj.domain.user.repository.UserRepository;
import com.tripj.global.error.exception.ForbiddenException;
import com.tripj.global.error.exception.NotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.List;

import static com.tripj.global.code.ErrorCode.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class BoardServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private TripService tripService;
    @Autowired
    private TripRepository tripRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CountryRepository countryRepository;
    @Autowired
    private BoardCateRepository boardCateRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;

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
        commentRepository.deleteAllInBatch();
        boardRepository.deleteAllInBatch();
        boardCateRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Nested
    class createBoard {

        @Test
        @DisplayName("게시글 등록에 성공합니다")
        void createBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());

            //when
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //then
            assertThat(board.getTitle()).isEqualTo("게시글 제목");
            assertThat(board.getContent()).isEqualTo("게시글 내용");
            assertThat(board.getBoardCateId()).isEqualTo(request.getBoardCateId());
        }

        @Test
        @DisplayName("존재 하지 않는 사용자가 등록시 예외를 발생시킵니다.")
        void createBoardNotExistingUser() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());

            //when //then
            assertThatThrownBy(() -> boardService.createBoard(request, 2L, null))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재 하지 않는 카테고리로 등록시 예외를 발생시킵니다.")
        void createBoardNotExistingCate() throws IOException {
            //given
            //FIXME : 3L로 request 하는데 왜 실패하지 ?
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", 3L);

            //when //then
            assertThatThrownBy(() -> boardService.createBoard(request, user.getId(), null))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD_CATE.getMessage());
        }
    }

    @Nested
    class updateBoard {

        @Test
        @DisplayName("게시글 수정에 성공합니다.")
        void updateBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when
            CreateBoardResponse updateBoardResponse = boardService.updateBoard(
                    CreateBoardRequest.builder()
                            .title("게시글 제목2")
                            .content("게시글 내용2")
                            .boardCateId(board.getBoardCateId())
                            .build(),
                    board.getBoardId(),
                    user.getId(),
                    null);

            //then
            assertThat(updateBoardResponse.getTitle()).isEqualTo("게시글 제목2");
            assertThat(updateBoardResponse.getContent()).isEqualTo("게시글 내용2");
        }

        @Test
        @DisplayName("존재하지 않는 사용자가 게시글 수정시 예외가 발생합니다.")
        void updateBoardNotExistingUser() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> boardService.updateBoard(
                    CreateBoardRequest.builder()
                            .title("게시글 제목2")
                            .content("게시글 내용2")
                            .boardCateId(board.getBoardCateId())
                            .build(),
                    board.getBoardId(),
                    2L,
                    null))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 게시글 수정시 예외가 발생합니다.")
        void updateBoardNotExistingBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> boardService.updateBoard(
                    CreateBoardRequest.builder()
                            .title("게시글 제목2")
                            .content("게시글 내용2")
                            .boardCateId(board.getBoardCateId())
                            .build(),
                    2L,
                    user.getId(),
                    null))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 게시글 카테고리로 수정시 예외가 발생합니다.")
        void updateBoardNotExistingBoardCate() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> boardService.updateBoard(
                    CreateBoardRequest.builder()
                            .title("게시글 제목2")
                            .content("게시글 내용2")
                            .boardCateId(3L)
                            .build(),
                    board.getBoardId(),
                    user.getId(),
                    null))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD_CATE.getMessage());
        }

        @Test
        @DisplayName("자신이 등록한 게시글이 아니면 수정시 예외가 발생합니다.")
        void updateNotMyBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            User user2 = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            //when //then
            assertThatThrownBy(() -> boardService.updateBoard(
                    CreateBoardRequest.builder()
                            .title("게시글 제목2")
                            .content("게시글 내용2")
                            .boardCateId(board.getBoardCateId())
                            .build(),
                    board.getBoardId(),
                    user2.getId(),
                    null))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(E403_NOT_MY_BOARD.getMessage());
        }
    }

    @Nested
    class deleteBoard {

        @Test
        @DisplayName("게시글 삭제에 성공합니다.")
        void deleteBoard() throws IOException{
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when
            DeleteBoardResponse deleteBoardResponse = boardService.deleteBoard(user.getId(), board.getBoardId());

            //then
            assertThat(deleteBoardResponse.getBoardId()).isEqualTo(board.getBoardId());
        }

        @Test
        @DisplayName("존재하지 않는 게시글 삭제시 예외가 발생합니다.")
        void deleteNotExistingBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> boardService.deleteBoard(user.getId(), 2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }

        @Test
        @DisplayName("자신의 게시글이 아니면 삭제시 예외가 발생합니다.")
        void deleteNotMyBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            User user2 = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            //when //then
            assertThatThrownBy(() -> boardService.deleteBoard(user2.getId(), board.getBoardId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(E403_NOT_MY_BOARD.getMessage());
        }
    }

    @Nested
    class getBoardDetail {

        @Test
        @DisplayName("게시글 상세 조회에 성공합니다.")
        void getBoardDetail() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when
            GetBoardDetailResponse boardDetail = boardService.getBoardDetail(board.getBoardId());

            //then
            assertThat(boardDetail).isNotNull();
            assertThat(boardDetail.getBoardId()).isEqualTo(board.getBoardId());
            assertThat(boardDetail.getTitle()).isEqualTo("게시글 제목");
            assertThat(boardDetail.getContent()).isEqualTo("게시글 내용");
            assertThat(boardDetail.getBoardCateName()).isEqualTo(boardCate.getBoardCateName());
        }

        @Test
        @DisplayName("존재하지 않는 게시글 조회시 예외가 발생합니다.")
        void getBoardDetailNotExistingBoard() throws IOException {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            //when //then
            assertThatThrownBy(() -> boardService.getBoardDetail(2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }
    }

    @Nested
    class GetBoardComment {

        @Test
        @DisplayName("게시글 댓글 조회에 성공합니다.")
        void getBoardComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentRequest commentRequest2 = createCommentRequest(board.getBoardId(), "댓글 내용2");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());
            CreateCommentResponse comment2 = commentService.createComment(commentRequest2, user.getId());

            //when
            List<GetBoardCommentResponse> boardComment = boardService.getBoardComment(board.getBoardId());

            //then
            assertThat(boardComment).hasSize(2)
                    .extracting("boardId", "content")
                    .containsExactlyInAnyOrder(
                            tuple(board.getBoardId(), "댓글 내용"),
                            tuple(board.getBoardId(), "댓글 내용2")
                    );
        }

        private CreateCommentRequest createCommentRequest(Long boardId, String content) {
            return CreateCommentRequest.builder()
                    .boardId(boardId)
                    .content(content)
                    .build();
        }
    }


    private CreateBoardRequest createBoardRequest(String title, String content, Long boardCateId) {
        return CreateBoardRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .boardCateId(boardCateId)
                .build();
    }

    private BoardCate createBoardCate(Long boardCateId, String boardCateCode, String boardCateName) {
        return BoardCate.builder()
                .id(boardCateId)
                .boardCateCode(boardCateCode)
                .boardCateName(boardCateName)
                .build();
    }

}