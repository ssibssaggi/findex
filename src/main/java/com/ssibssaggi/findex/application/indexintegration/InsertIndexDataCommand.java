package com.ssibssaggi.findex.application.indexintegration;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.ssibssaggi.findex.client.openapi.dto.indexdata.IndexDataFetchResult;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public record InsertIndexDataCommand(
        IndexInformation indexInformation,
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
    public static InsertIndexDataCommand from(
            IndexDataFetchResult indexDataApiItem,
            IndexInformation indexInformation
    ) {
        return new InsertIndexDataCommand(
                indexInformation,
                indexDataApiItem.baseDate(),
                indexDataApiItem.marketPrice(),
                indexDataApiItem.closingPrice(),
                indexDataApiItem.highPrice(),
                indexDataApiItem.lowPrice(),
                indexDataApiItem.versus(),
                indexDataApiItem.fluctuationRate(),
                indexDataApiItem.tradingQuantity(),
                indexDataApiItem.tradingPrice(),
                indexDataApiItem.marketTotalAmount()
        );
    }

    public IndexData toIndexData() {
        return IndexData.createWithOpenApi(
                indexInformation,
                baseDate,
                marketPrice,
                closingPrice,
                highPrice,
                lowPrice,
                versus,
                fluctuationRate,
                tradingQuantity,
                tradingPrice,
                marketTotalAmount
        );
    }
}
