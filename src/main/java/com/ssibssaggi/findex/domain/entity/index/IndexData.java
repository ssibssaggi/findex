package com.ssibssaggi.findex.domain.entity.index;

import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataUpdateRequest;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"index_information", "base_date"})
})
public class IndexData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate baseDate;
    private SourceType sourceType;
    private BigDecimal marketPrice;
    private BigDecimal closingPrice;
    private BigDecimal highPrice;
    private BigDecimal lowPrice;
    private BigDecimal versus;
    private BigDecimal fluctuationRate;
    private Long tradingQuantity;
    private Long tradingPrice;
    private Long marketTotalAmount;

    @ManyToOne
    @JoinColumn(name = "index_information_id")
    private IndexInformation indexInformation;

    public static IndexData createWithOpenApi(
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
        return new IndexData(
                null,
                baseDate,
                SourceType.OPEN_API,
                marketPrice,
                closingPrice,
                highPrice,
                lowPrice,
                versus,
                fluctuationRate,
                tradingQuantity,
                tradingPrice,
                marketTotalAmount,
                indexInformation
        );
    }

    public static IndexData create (IndexDataCreateRequest createRequest, IndexInformation indexInformation, SourceType sourceType) {
        IndexData entity = new IndexData();
        entity.baseDate = createRequest.baseDate();
        entity.sourceType = sourceType;
        entity.marketPrice = createRequest.marketPrice();
        entity.closingPrice = createRequest.closingPrice();
        entity.highPrice = createRequest.highPrice();
        entity.lowPrice = createRequest.lowPrice();
        entity.versus = createRequest.versus();
        entity.fluctuationRate = createRequest.fluctuationRate();
        entity.tradingQuantity = createRequest.tradingQuantity();
        entity.tradingPrice = createRequest.tradingPrice();
        entity.marketTotalAmount = createRequest.marketTotalAmount();
        entity.indexInformation = indexInformation;
        return entity;
    }

    public void update (IndexDataUpdateRequest updateRequest, IndexInformation indexInformation) {
        this.baseDate = updateRequest.baseDate();
        this.marketPrice = updateRequest.marketPrice();
        this.closingPrice = updateRequest.closingPrice();
        this.highPrice = updateRequest.highPrice();
        this.lowPrice = updateRequest.lowPrice();
        this.versus = updateRequest.versus();
        this.fluctuationRate = updateRequest.fluctuationRate();
        this.tradingQuantity = updateRequest.tradingQuantity();
        this.tradingPrice = updateRequest.tradingPrice();
        this.marketTotalAmount = updateRequest.marketTotalAmount();
        this.indexInformation = indexInformation;
    }
}
