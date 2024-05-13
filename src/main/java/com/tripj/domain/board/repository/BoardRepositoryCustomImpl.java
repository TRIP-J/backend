package com.tripj.domain.board.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.board.model.dto.response.GetBoardResponse;
import com.tripj.domain.board.model.dto.response.QGetBoardResponse;
import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.model.entity.QBoard;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.tripj.domain.board.model.entity.QBoard.board;
import static com.tripj.domain.comment.model.entity.QComment.comment;
import static com.tripj.domain.like.model.entity.QLikedBoard.likedBoard;
import static com.tripj.domain.user.model.entity.QUser.user;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Slice<GetBoardResponse> findAllPaging(Long lastBoardId, Pageable pageable) {
        List<GetBoardResponse> results = queryFactory
                .select((new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        comment.id.countDistinct(),
                        likedBoard.id.countDistinct()
                )))
                .from(board)
                .join(board.user, user)
                .leftJoin(board.comment, comment)
                .leftJoin(board.likedBoard, likedBoard)
                .groupBy(user.id, user.userName, user.profile, board.id,
                        board.boardCate.boardCateName, board.title,
                        board.content, board.regTime)
                .orderBy(board.regTime.desc())
                .limit(pageable.getPageSize() + 1)
                .fetch();

        return checkLastPage(pageable, results);
    }

    private BooleanExpression lastBoardId(Long boardId) {
        if (boardId == null) return null;
        return board.id.lt(boardId);
    }

    private Slice<GetBoardResponse> checkLastPage(Pageable pageable, List<GetBoardResponse> results) {
        boolean hasNext = false;

        if (results.size() > pageable.getPageSize()) {
            hasNext = true;
            results.remove(pageable.getPageSize());
        }

        return new SliceImpl<>(results, pageable, hasNext);
    }

    @Override
    public GetBoardResponse getBoardDetail(Long boardId) {

        GetBoardResponse result = queryFactory
                .select(new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        JPAExpressions
                                .select(comment.id.countDistinct())
                                .from(comment)
                                .where(comment.board.id.eq(board.id)),
                        JPAExpressions
                                .select(likedBoard.id.countDistinct())
                                .from(likedBoard)
                                .where(likedBoard.board.id.eq(board.id))
                ))
                .from(board)
                .join(board.user, user)
                .where(board.id.eq(boardId))
                .groupBy(user.id, user.userName, user.profile,
                        board.id, board.boardCate.boardCateName,
                        board.title, board.content)
                .fetchOne();

        return result;
    }

    @Override
    public List<GetBoardResponse> getBoardList(Long boardCateId) {
        List<GetBoardResponse> results = queryFactory
                .select((new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        comment.id.countDistinct(),
                        likedBoard.id.countDistinct()
                )))
                .from(board)
                .join(board.user, user)
                .leftJoin(board.comment, comment)
                .leftJoin(board.likedBoard, likedBoard)
                .where(board.boardCate.id.eq(boardCateId))
                .groupBy(user.id, user.userName, user.profile, board.id,
                        board.boardCate.boardCateName, board.title,
                        board.content, board.regTime)
                .fetch();

        return results;
    }

    @Override
    public List<GetBoardResponse> getBoardLatestList() {
        List<GetBoardResponse> results = queryFactory
                .select((new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        comment.id.countDistinct(),
                        likedBoard.id.countDistinct()
                )))
                .from(board)
                .join(board.user, user)
                .leftJoin(board.comment, comment)
                .leftJoin(board.likedBoard, likedBoard)
                .groupBy(user.id, user.userName, user.profile, board.id,
                        board.boardCate.boardCateName, board.title,
                        board.content, board.regTime)
                .orderBy(board.regTime.desc())
                .fetch();

        return results;
    }

    @Override
    public List<GetBoardResponse> getBoardPopularList() {
        List<GetBoardResponse> results = queryFactory
                .select((new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        comment.id.countDistinct(),
                        likedBoard.id.countDistinct()
                )))
                .from(board)
                .join(board.user, user)
                .leftJoin(board.comment, comment)
                .leftJoin(board.likedBoard, likedBoard)
                .groupBy(user.id, user.userName, user.profile, board.id,
                        board.boardCate.boardCateName, board.title,
                        board.content, board.regTime)
                .orderBy(likedBoard.id.countDistinct().desc(),
                        board.regTime.desc())
                .fetch();

        return results;
    }

    @Override
    public List<GetBoardResponse> getMyBoardList(Long userId) {
        List<GetBoardResponse> results = queryFactory
                .select((new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.boardCate.boardCateName,
                        board.title,
                        board.content,
                        board.regTime,
                        comment.id.countDistinct(),
                        likedBoard.id.countDistinct()
                )))
                .from(board)
                .join(board.user, user)
                .leftJoin(board.comment, comment)
                .leftJoin(board.likedBoard, likedBoard)
                .where(board.user.id.eq(userId))
                .groupBy(user.id, user.userName, user.profile, board.id,
                        board.boardCate.boardCateName, board.title,
                        board.content, board.regTime)
                .orderBy(board.regTime.desc())
                .fetch();

        return results;
    }

}
