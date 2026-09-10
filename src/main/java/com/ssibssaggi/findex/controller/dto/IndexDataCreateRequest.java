package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;

public record IndexDataCreateRequest(
        Long indexInformationId,
        LocalDate baseDate,
        Float marketPrice,
        Float closingPrice,
        Float highPrice,
        Float lowPrice,
        Float versus,
        Float fluctuationRate,
        Long tradingQuantity,
        Long tradingPrice,
        Long marketTotalAmount
) {

}