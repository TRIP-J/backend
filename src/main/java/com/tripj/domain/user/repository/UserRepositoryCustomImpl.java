package com.tripj.domain.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

public class UserRepositoryCustomImpl implements UserRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }



}
