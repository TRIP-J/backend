package com.tripj.domain.board.repository;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.board.model.dto.response.GetBoardResponse;
import com.tripj.domain.board.model.dto.response.QGetBoardResponse;
import com.tripj.domain.board.model.entity.Board;
import com.tripj.domain.board.model.entity.QBoard;
import com.tripj.domain.user.model.entity.QUser;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static com.tripj.domain.board.model.entity.QBoard.*;
import static com.tripj.domain.user.model.entity.QUser.*;

public class BoardRepositoryCustomImpl implements BoardRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public BoardRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }


    @Override
    public Slice<GetBoardResponse> findAllPaging(Long lastBoardId, Pageable pageable) {

        List<GetBoardResponse> results = queryFactory
                .select(new QGetBoardResponse(
                        user.id,
                        user.userName,
                        user.profile,
                        board.id,
                        board.title,
                        board.content
                ))
                .from(board)
                .join(board.user, user)
                .where(
                        lastBoardId(lastBoardId)
                )
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


}
