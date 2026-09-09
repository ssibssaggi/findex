package com.ssibssaggi.findex.controller.dto;

public record IndexInformationCreateRequest(
        String indexName,
        String indexClassification,
        Integer employedItemsCount,
        String basePointInTime,
        Number baseIndex,
        Boolean favorite
) {

}