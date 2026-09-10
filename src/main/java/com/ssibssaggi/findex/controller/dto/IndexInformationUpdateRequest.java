package com.ssibssaggi.findex.controller.dto;

public record IndexInformationUpdateRequest(
        Integer employedItemsCount,
        String basePointInTime,
        Float baseIndex,
        Boolean favorite
) {
}