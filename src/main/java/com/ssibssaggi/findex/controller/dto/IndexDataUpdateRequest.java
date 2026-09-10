package com.ssibssaggi.findex.controller.dto;

public record IndexDataUpdateRequest(
        Float marketPrice,
        Float closingPrice,
        Float highPrice,
        Float lowPrice,
        Float versus,
        Float fluctuationRate,
        Long tradingQuantity,     // Long으로 통일
        Long tradingPrice,        // Long으로 통일
        Long marketTotalAmount    // Long으로 통일
) {
}