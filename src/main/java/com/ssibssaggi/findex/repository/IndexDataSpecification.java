package com.ssibssaggi.findex.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.domain.Specification;

import com.ssibssaggi.findex.domain.entity.index.IndexData;

public class IndexDataSpecification {

    public static Specification<IndexData> createIndexInformationIdSpecification(Long indexInformationId) {
        return (root, query, cb) -> indexInformationId == null
                ? null
                : cb.equal(root.get("indexInformation").get("id"), indexInformationId);
    }

    public static Specification<IndexData> createBaseDateFromSpecification(LocalDate fromDate) {
        return (root, query, cb) -> fromDate == null
                ? null
                : cb.greaterThanOrEqualTo(root.get("baseDate"), fromDate);
    }

    public static Specification<IndexData> createBaseDateToSpecification(LocalDate toDate) {
        return (root, query, cb) -> toDate == null
                ? null
                : cb.lessThanOrEqualTo(root.get("baseDate"), toDate);
    }
}
