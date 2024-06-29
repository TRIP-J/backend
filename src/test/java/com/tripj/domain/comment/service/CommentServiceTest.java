package com.tripj.domain.comment.service;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.response.CreateBoardResponse;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.board.service.BoardService;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.comment.model.dto.request.CreateCommentRequest;
import com.tripj.domain.comment.model.dto.response.CreateCommentResponse;
import com.tripj.domain.comment.model.dto.response.DeleteCommentResponse;
import com.tripj.domain.comment.repository.CommentRepository;
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

import static com.tripj.global.code.ErrorCode.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@SpringBootTest
@ActiveProfiles("test")
class CommentServiceTest {

    @Autowired
    private BoardService boardService;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private UserRepository userRepository;
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
    class createComment {

        @Test
        @DisplayName("게시글 댓글 등록에 성공합니다.")
        void createComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");

            //when
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //then
            assertThat(comment.getCommentId()).isNotNull();
            assertThat(comment.getBoardId()).isEqualTo(board.getBoardId());
            assertThat(comment.getContent()).isEqualTo("댓글 내용");
        }

        @Test
        @DisplayName("존재하지 않는 회원이 게시글에 댓글 등록시 예외가 발생합니다.")
        void createCommentNotExistingUser() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");

            //when //then
            assertThatThrownBy(() -> commentService.createComment(commentRequest, 2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재하지 게시글에 댓글 등록시 예외가 발생합니다.")
        void createCommentNotExistingBoard() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(2L, "댓글 내용");

            //when //then
            assertThatThrownBy(() -> commentService.createComment(commentRequest, user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }
    }

    @Nested
    class updateComment {

        @Test
        @DisplayName("게시글 댓글 수정에 성공합니다.")
        void updateComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when
            CreateCommentResponse updateResponse = commentService.updateComment(
                    createCommentRequest(board.getBoardId(), "수정된 댓글 내용"),
                    comment.getCommentId(),
                    user.getId());

            //then
            assertThat(updateResponse).isNotNull();
            assertThat(updateResponse.getCommentId()).isEqualTo(comment.getCommentId());
            assertThat(updateResponse.getBoardId()).isEqualTo(board.getBoardId());
            assertThat(updateResponse.getContent()).isEqualTo("수정된 댓글 내용");
        }

        @Test
        @DisplayName("존재하지 않는 사용자가 게시글 댓글 수정시 예외가 발생합니다.")
        void updateCommentNotExistingUser() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.updateComment(
                    createCommentRequest(board.getBoardId(), "수정된 댓글 내용"),
                    comment.getCommentId(),
                    2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_USER.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 게시글에 댓글 수정시 예외가 발생합니다.")
        void updateCommentNotExistingBoard() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.updateComment(
                    createCommentRequest(2L, "수정된 댓글 내용"),
                    comment.getCommentId(),
                    user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }

        @Test
        @DisplayName("존재하지 않는 댓글을 수정시 예외가 발생합니다.")
        void updateNotExistingComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.updateComment(
                    createCommentRequest(board.getBoardId(), "수정된 댓글 내용"),
                    2L,
                    user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_COMMENT.getMessage());
        }

        @Test
        @DisplayName("자신이 작성한 댓글이 아니면 수정시 예외가 발생합니다.")
        void updateNotMyComment() throws Exception {
            //given
            User user2 = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.updateComment(
                    createCommentRequest(board.getBoardId(), "수정된 댓글 내용"),
                    comment.getCommentId(),
                    user2.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(E403_NOT_MY_COMMENT.getMessage());
        }
    }

    @Nested
    class deleteComment {

        @Test
        @DisplayName("게시글 댓글 삭제가 성공합니다.")
        void deleteComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when
            DeleteCommentResponse deleteCommentResponse = commentService.deleteComment(comment.getCommentId(), user.getId());

            //then
            assertThat(deleteCommentResponse.getCommentId()).isEqualTo(comment.getCommentId());
            assertThat(deleteCommentResponse.getBoardId()).isEqualTo(comment.getBoardId());
        }

        @Test
        @DisplayName("존재하지 않는 게시글 댓글 삭제시 예외가 발생합니다.")
        void deleteNotExistingComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.deleteComment(99L, user.getId()))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_COMMENT.getMessage());
        }

        @Test
        @DisplayName("자신의 댓글이 아니면 삭제시 예외를 발생합니다.")
        void deleteNotMyComment() throws Exception {
            //given
            User user2 = userRepository.save(
                    User.builder()
                            .userType(UserType.KAKAO)
                            .email("asdf2@naver.com")
                            .nickname("다람지기엽지2")
                            .userName("홍길동2")
                            .role(Role.ROLE_USER)
                            .build());

            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentResponse comment = commentService.createComment(commentRequest, user.getId());

            //when //then
            assertThatThrownBy(() -> commentService.deleteComment(comment.getCommentId(), user2.getId()))
                    .isInstanceOf(ForbiddenException.class)
                    .hasMessage(E403_NOT_MY_COMMENT.getMessage());
        }

    }

    private CreateBoardRequest createBoardRequest(String title, String content, Long boardCateId) {
        return CreateBoardRequest.builder()
                .title("게시글 제목")
                .content("게시글 내용")
                .boardCateId(boardCateId)
                .build();
    }

    private CreateCommentRequest createCommentRequest(Long boardId, String content) {
        return CreateCommentRequest.builder()
                .boardId(boardId)
                .content(content)
                .build();
    }


}