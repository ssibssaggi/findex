package com.ssibssaggi.findex.controller.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record IndexDataExportResponse(
        LocalDate baseDate,
        BigDecimal marketPrice,
        BigDecimal closingPrice,
        BigDecimal highPrice,
        BigDecimal lowPrice,
        BigDecimal versus,
        BigDecimal fluctuationRate,
        Long tradingQuantity,
        Long tradingPrice,
        Long marketTotalAmount
) {

}
