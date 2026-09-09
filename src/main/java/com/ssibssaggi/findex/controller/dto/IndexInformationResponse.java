package com.ssibssaggi.findex.controller.dto;

import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public record IndexInformationResponse(
        Long id,
        String indexClassification,
        String indexName,
        Integer employedItemsCount,
        String basePointInTime,
        Number baseIndex,
        String sourceType,
        Boolean favorite
) {

    public static IndexInformationResponse of(IndexInformation entity) {
        return new IndexInformationResponse(
                entity.getId(),
                entity.getIndexClassification(),
                entity.getIndexName(),
                entity.getEmployedItemsCount(),
                entity.getBasePointInTime().toString(),
                entity.getBaseIndex(),
                entity.getSourceType().toString(),
                entity.getFavorite()
        );
    }
}
