package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;

// 주요 지수 현황 요약 응답 DTO
public record IndexPerformanceSummaryResponse(
        Long indexInformationId,
        String indexClassification,
        String indexName,
        LocalDate baseDate,
        Float closingPrice,
        Float versus,
        Float fluctuationRate
) {
}
