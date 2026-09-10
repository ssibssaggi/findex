package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;
import java.util.List;

// 지수 차트 응답 DTO
public record IndexChartResponse(
        Long indexInformationId,
        String indexName,
        ChartPeriodType periodType,
        List<ChartPoint> chartPoints
) {
    public record ChartPoint(
            LocalDate baseDate,
            Float closingPrice,
            Float movingAverage5,
            Float movingAverage20
    ) {
    }
}
