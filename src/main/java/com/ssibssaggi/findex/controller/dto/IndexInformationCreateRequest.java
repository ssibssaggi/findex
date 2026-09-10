package com.ssibssaggi.findex.controller.dto;

public record IndexInformationCreateRequest(
        String indexName,
        String indexClassification,
        Integer employedItemsCount,
        String basePointInTime,
        Float baseIndex,
        Boolean favorite
) {

}