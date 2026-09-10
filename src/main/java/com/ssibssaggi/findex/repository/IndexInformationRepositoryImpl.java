package com.ssibssaggi.findex.repository;

import java.util.List;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.ComparableExpressionBase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssibssaggi.findex.controller.dto.CursorPageCondition;
import com.ssibssaggi.findex.controller.dto.IndexInfoSearchCondition;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.QIndexInformation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IndexInformationRepositoryImpl implements IndexInformationRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<IndexInformation> searchIndexInfos(
            IndexInfoSearchCondition indexInfoSearchCondition,
            CursorPageCondition cursorPageCondition
    ) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return jpaQueryFactory
                .select(indexInformation)
                .from(indexInformation)
                .where(
                        indexClassificationContains(indexInfoSearchCondition.indexClassification()),
                        indexNameContains(indexInfoSearchCondition.indexName()),
                        favoriteEquals(indexInfoSearchCondition.favorite())
                )
                .orderBy(createOrderSpecifier(cursorPageCondition.sortField(), cursorPageCondition.sortDirection()))
                .limit(cursorPageCondition.size() + 1)
                .fetch();
    }

    @Override
    public Long count(IndexInfoSearchCondition searchCondition) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return jpaQueryFactory
                .select(indexInformation.count())
                .from(indexInformation)
                .where(
                        indexClassificationContains(searchCondition.indexClassification()),
                        indexNameContains(searchCondition.indexName()),
                        favoriteEquals(searchCondition.favorite())
                )
                .fetchOne();
    }

    // 조건절 (WHERE)
    // BooleanExpression : Null 이면 자동으로 조건절에서 제외되는 DSL 제공 타입
    // - value가 null이면 조건 자체를 안 붙임
    // - contains : LIKE '%value%'
    // - eq(x) : ? = x
    // - gt(x) : ? > x
    // - lt(x) : ? < x
    // - goe(x) : ? >= x
    // - loe(x) : ? <= x
    private BooleanExpression indexClassificationContains(String value) {
        return value == null ? null : QIndexInformation.indexInformation.indexClassification.contains(value);
    }

    private BooleanExpression indexNameContains(String value) {
        return value == null ? null : QIndexInformation.indexInformation.indexName.contains(value);
    }

    private BooleanExpression favoriteEquals(Boolean value) {
        return value == null ? null : QIndexInformation.indexInformation.favorite.eq(value);
    }

    private BooleanExpression cursorCondition(CursorPageCondition condition) {
        if (condition.idAfter() == null) {
            return null;
        }
        QIndexInformation q = QIndexInformation.indexInformation;
        boolean isDesc = "DESC".equalsIgnoreCase(condition.sortDirection());
        ComparableExpressionBase<?> sortTarget = getSortTarget(condition.sortField());
        Comparable cursorValue = condition.cursor();

        return null;
        //        return switch (condition.sortField()) {
        //            case "indexClassification" -> {
        //                String cursor = condition.cursor();
        //                yield isDesc
        //                        ? q.indexClassification.lt(cursor)
        //                        .or(q.indexClassification.eq(cursor).and(q.id.lt(conditionidAfter)))
        //                        : q.indexClassification.gt(cursor).or(q.indexClassification.eq(cursor).and(q.id.gt
        //                        (idAfter)));
        //            }
        //            case "indexName" -> {
        //                String cursor = condition.cursor();
        //                yield isDesc
        //                        ? q.indexName.lt(cursor).or(q.indexName.eq(cursor).and(q.id.lt(idAfter)))
        //                        : q.indexName.gt(cursor).or(q.indexName.eq(cursor).and(q.id.gt(idAfter)));
        //            }
        //            case "employedItemsCount" -> {
        //                Integer cursor = Integer.valueOf(condition.cursor());
        //                yield isDesc
        //                        ? q.employedItemsCount.lt(cursor).or(q.employedItemsCount.eq(cursor).and(q.id.lt
        //                        (idAfter)))
        //                        : q.employedItemsCount.gt(cursor).or(q.employedItemsCount.eq(cursor).and(q.id.gt
        //                        (idAfter)));
        //            }
        //            default -> isDesc ? q.id.lt(idAfter) : q.id.gt(idAfter);
        //        };
    }

    // 정렬 (OrderBy)
    // sortField 값에 따라 ORDER BY 대상 컬럼이 달라짐: 기본 정렬 컬럼 - id
    // sortDirection == "desc" (대소문자 무관) -> DESC, 아니면 ASC
    private OrderSpecifier<?> createOrderSpecifier(String sortField, String sortDirection) {
        ComparableExpressionBase<?> target = getSortTarget(sortField);
        Order direction = "DESC".equalsIgnoreCase(sortDirection) ? Order.DESC : Order.ASC;

        return new OrderSpecifier<>(direction, target);
    }

    private ComparableExpressionBase<?> getSortTarget(String sortField) {
        QIndexInformation indexInformation = QIndexInformation.indexInformation;

        return switch (sortField) {
            case "indexClassification" -> indexInformation.indexClassification;
            case "indexName" -> indexInformation.indexName;
            case "employedItemsCount" -> indexInformation.employedItemsCount;
            default -> indexInformation.id;
        };
    }
}
