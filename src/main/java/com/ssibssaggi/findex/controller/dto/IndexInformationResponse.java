package com.ssibssaggi.findex.controller.dto;

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
}
