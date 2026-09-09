package com.ssibssaggi.findex.controller.dto;

public record IndexInformationUpdateRequest(
        Integer employedItemsCount,
        String basePointInTime,
        Integer baseIndex,
        Boolean favorite
) {
}