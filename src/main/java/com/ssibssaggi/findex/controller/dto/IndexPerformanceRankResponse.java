package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;

// 지수 성과 랭킹 응답 DTO
public record IndexPerformanceRankResponse(
        int rank,
        Long indexInformationId,
        String indexName,
        LocalDate baseDate,
        Float closingPrice,
        LocalDate beforeBaseDate,
        Float beforeClosingPrice,
        Float versus,
        Float fluctuationRate
) {
}
