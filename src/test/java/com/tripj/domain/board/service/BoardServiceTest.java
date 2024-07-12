package com.tripj.domain.board.service;

import com.tripj.domain.board.model.dto.request.CreateBoardRequest;
import com.tripj.domain.board.model.dto.request.GetBoardSearchRequest;
import com.tripj.domain.board.model.dto.response.*;
import com.tripj.domain.board.repository.BoardRepository;
import com.tripj.domain.boardcate.model.entity.BoardCate;
import com.tripj.domain.boardcate.repository.BoardCateRepository;
import com.tripj.domain.comment.model.dto.request.CreateCommentRequest;
import com.tripj.domain.comment.repository.CommentRepository;
import com.tripj.domain.comment.service.CommentService;
import com.tripj.domain.like.model.dto.request.CreateLikedBoardRequest;
import com.tripj.domain.like.model.dto.response.CreateLikedBoardResponse;
import com.tripj.domain.like.repository.LikedBoardRepository;
import com.tripj.domain.like.service.LikedBoardService;
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
    private UserRepository userRepository;
    @Autowired
    private BoardCateRepository boardCateRepository;
    @Autowired
    private CommentService commentService;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikedBoardService likedBoardService;
    @Autowired
    private LikedBoardRepository likedBoardRepository;

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
        likedBoardRepository.deleteAllInBatch();
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
        @DisplayName("존재 하지 않는 사용자가 등록시예외를 발생시킵니다.")
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
        void deleteBoard() throws IOException {
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
            commentService.createComment(commentRequest, user.getId());
            commentService.createComment(commentRequest2, user.getId());

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

        @Test
        @DisplayName("존재하지 않는 게시글의 댓글 조회시 예외가 발생합니다.")
        void getNotExistingBoardComment() throws Exception {
            //given
            CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
            CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

            CreateCommentRequest commentRequest = createCommentRequest(board.getBoardId(), "댓글 내용");
            CreateCommentRequest commentRequest2 = createCommentRequest(board.getBoardId(), "댓글 내용2");
            commentService.createComment(commentRequest, user.getId());
            commentService.createComment(commentRequest2, user.getId());

            //when //then
            assertThatThrownBy(() -> boardService.getBoardComment(2L))
                    .isInstanceOf(NotFoundException.class)
                    .hasMessage(E404_NOT_EXISTS_BOARD.getMessage());
        }
    }

    @Test
    @DisplayName("(후기,질문,꿀팁) 카테고리별 게시글 전체 리스트 조회에 성공합니다.")
    void getBoardList() throws Exception {
        //given
        BoardCate boardCate2 = boardCateRepository.save(
                BoardCate.builder()
                        .boardCateCode("TIP")
                        .boardCateName("꿀팁")
                        .build());

        CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
        CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

        CreateBoardRequest request2 = createBoardRequest("게시글 제목2", "게시글 내용2", boardCate.getId());
        CreateBoardResponse board2 = boardService.createBoard(request2, user.getId(), null);

        CreateBoardRequest request3 = createBoardRequest("게시글 제목3", "게시글 내용3", boardCate2.getId());
        boardService.createBoard(request3, user.getId(), null);

        //when
        List<GetBoardResponse> boardList = boardService.getBoardList(boardCate.getId());

        //then
        assertThat(boardList).hasSize(2)
                .extracting("boardId", "title", "content", "boardCateId")
                .containsExactlyInAnyOrder(
                        tuple(board.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId()),
                        tuple(board2.getBoardId(), "게시글 제목2", "게시글 내용2", boardCate.getId())
                );
    }

    @Test
    @DisplayName("최신글 전체 리스트 조회에 성공합니다.")
    void getBoardLatestList() throws Exception {
        //given
        CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
        CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

        CreateBoardRequest request2 = createBoardRequest("게시글 제목2", "게시글 내용2", boardCate.getId());
        CreateBoardResponse board2 = boardService.createBoard(request2, user.getId(), null);

        //when
        List<GetBoardResponse> boardList = boardService.getBoardLatestList();

        //then
        assertThat(boardList).hasSize(2)
                .extracting("boardId", "title", "content", "boardCateId")
                .containsExactly(
                        tuple(board2.getBoardId(), "게시글 제목2", "게시글 내용2", boardCate.getId()),
                        tuple(board.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId())
                );

        assertThat(boardList.get(0).getRegTime()).isAfter(boardList.get(1).getRegTime());
    }

    @Test
    @DisplayName("게시글 전체 검색 조회에 성공합니다.")
    void getAllBoardList() throws Exception {
        //given
        CreateBoardRequest request = createBoardRequest("게시글 제목", "게시글 내용", boardCate.getId());
        CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);

        CreateBoardRequest request2 = createBoardRequest("게시굴 제목", "게시굴 내용", boardCate.getId());
        boardService.createBoard(request2, user.getId(), null);

        //when
        List<GetBoardResponse> boardList = boardService.getAllBoardList(new GetBoardSearchRequest("게시글"));

        //then
        assertThat(boardList).hasSize(1)
                .extracting("boardId", "title", "content", "boardCateId")
                .containsExactlyInAnyOrder(
                        tuple(board.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId())
                );
    }

    @Test
    @DisplayName("내 게시글 조회에 성공합니다.")
    void getMyBoardList() throws Exception {
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

        CreateBoardRequest request2 = createBoardRequest("게시글 제목2", "게시글 내용2", boardCate.getId());
        boardService.createBoard(request2, user2.getId(), null);

        //when
        List<GetBoardResponse> boardList = boardService.getMyBoardList(user.getId());

        //then
        assertThat(boardList).hasSize(1)
                .extracting("boardId", "title", "content", "boardCateId")
                .containsExactlyInAnyOrder(
                        tuple(board.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId())
                );
    }

    @Test
    @DisplayName("인기글 전체 리스트 조회에 성공합니다.")
    void getBoardPopularList() throws Exception {
        User user2 = userRepository.save(
                User.builder()
                        .userType(UserType.KAKAO)
                        .email("asdf2@naver.com")
                        .nickname("다람지기엽지2")
                        .userName("홍길동2")
                        .role(Role.ROLE_USER)
                        .build());

        //given
        CreateLikedBoardResponse likedBoard = createLikedBoard("게시글 제목", "게시글 내용", user.getId());
        CreateLikedBoardResponse likedBoard2 = createLikedBoard("게시글 제목2", "게시글 내용2", user2.getId());
        likedBoardService.createLikedBoard(new CreateLikedBoardRequest(likedBoard.getBoardId()), user2.getId());

        //when
        List<GetBoardResponse> boardPopularList = boardService.getBoardPopularList();

        //then
        assertThat(boardPopularList).hasSize(2)
                .extracting("userId", "boardId", "title", "content", "boardCateId", "likeCnt")
                .containsExactly(
                        tuple(user.getId(), likedBoard.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId(), 2L),
                        tuple(user.getId(), likedBoard2.getBoardId(), "게시글 제목2", "게시글 내용2", boardCate.getId(), 1L)
                );
    }

    @Test
    @DisplayName("내 좋아요 리스트 조회에 성공합니다.")
    void getMyLikedBoard() throws Exception {
        //given
        CreateLikedBoardResponse likedBoard = createLikedBoard("게시글 제목", "게시글 내용", user.getId());
        CreateLikedBoardResponse likedBoard2 = createLikedBoard("게시글 제목2", "게시글 내용2", user.getId());

        //when
        List<GetBoardResponse> myLikedBoard = boardService.getMyLikedBoard(user.getId());

        //then
        assertThat(myLikedBoard).hasSize(2)
                .extracting("userId", "boardId", "title", "content", "boardCateId")
                .containsExactly(
                        tuple(user.getId(), likedBoard2.getBoardId(), "게시글 제목2", "게시글 내용2", boardCate.getId()),
                        tuple(user.getId(), likedBoard.getBoardId(), "게시글 제목", "게시글 내용", boardCate.getId())
                );
    }

    private CreateLikedBoardResponse createLikedBoard(String title, String content, Long userId) throws IOException {
        CreateBoardRequest request = createBoardRequest(title, content, boardCate.getId());
        CreateBoardResponse board = boardService.createBoard(request, user.getId(), null);
        CreateLikedBoardResponse likedBoard =
                likedBoardService.createLikedBoard(new CreateLikedBoardRequest(board.getBoardId()), userId);
        return likedBoard;
    }

    private CreateCommentRequest createCommentRequest(Long boardId, String content) {
        return CreateCommentRequest.builder()
                .boardId(boardId)
                .content(content)
                .build();
    }

    private CreateBoardRequest createBoardRequest(String title, String content, Long boardCateId) {
        return CreateBoardRequest.builder()
                .title(title)
                .content(content)
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