package com.tripj.domain.precation.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.tripj.domain.precation.model.dto.GetPrecautionDetailResponse;
import com.tripj.domain.precation.model.dto.QGetPrecautionDetailResponse;
import com.tripj.domain.precation.model.entity.QPrecaution;

import java.util.List;

import static com.tripj.domain.precation.model.entity.QPrecaution.*;

public class PrecautionRepositoryCustomImpl implements PrecautionRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PrecautionRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public GetPrecautionDetailResponse getPrecautionDetail(Long precautionId) {
        GetPrecautionDetailResponse result = queryFactory
                .select(new QGetPrecautionDetailResponse(
                        precaution.title,
                        precaution.content
                ))
                .from(precaution)
                .where(precaution.id.eq(precautionId))
                .fetchOne();

        return result;
    }
}
