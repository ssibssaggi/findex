package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;

import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.SourceType;

// 지수 데이터(IndexData) 응답 DTO
// 엔티티를 그대로 노출하지 않고, 필요한 필드만 담아 반환하기 위한 용도
public record IndexDataResponse(
        Long id,
        Long indexInformationId,
        LocalDate baseDate,
        SourceType sourceType,
        Float marketPrice,
        Float closingPrice,
        Float highPrice,
        Float lowPrice,
        Float versus,
        Float fluctuationRate,
        Integer tradingQuantity,
        Integer tradingPrice,
        Integer marketTotalAmount
) {

    // IndexData 엔티티 -> IndexDataResponse 변환
    public static IndexDataResponse fromIndexData(IndexData indexData) {
        return new IndexDataResponse(
                indexData.getId(),
                indexData.getIndexInformation().getId(),
                indexData.getBaseDate(),
                indexData.getSourceType(),
                indexData.getMarketPrice(),
                indexData.getClosingPrice(),
                indexData.getHighPrice(),
                indexData.getLowPrice(),
                indexData.getVersus(),
                indexData.getFluctuationRate(),
                indexData.getTradingQuantity(),
                indexData.getTradingPrice(),
                indexData.getMarketTotalAmount()
        );
    }
}
