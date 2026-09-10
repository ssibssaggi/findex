package com.ssibssaggi.findex.controller.dto;

public record IndexDataUpdateRequest(
        Float marketPrice,
        Float closingPrice,
        Float highPrice,
        Float lowPrice,
        Float versus,
        Float fluctuationRate,
        Integer tradingQuantity,     // Long으로 통일
        Integer tradingPrice,        // Long으로 통일
        Integer marketTotalAmount    // Long으로 통일
) {
}