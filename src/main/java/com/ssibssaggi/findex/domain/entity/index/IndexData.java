package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class IndexData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate baseDate;
    private SourceType sourceType;
    private Float marketPrice;
    private Float closingPrice;
    private Float highPrice;
    private Float lowPrice;
    private Float versus;
    private Float fluctuationRate;
    private Integer tradingQuantity;
    private Integer tradingPrice;
    private Integer marketTotalAmount;

    @ManyToOne
    @JoinColumn(name = "index_information_id")
    private IndexInformation indexInformation;

    public void update(Float marketPrice, Float closingPrice, Float highPrice, Float lowPrice,
            Float versus, Float fluctuationRate, Integer tradingQuantity, Integer tradingPrice,
            Integer marketTotalAmount) {
        this.marketPrice = marketPrice;
        this.closingPrice = closingPrice;
        this.highPrice = highPrice;
        this.lowPrice = lowPrice;
        this.versus = versus;
        this.fluctuationRate = fluctuationRate;
        this.tradingQuantity = tradingQuantity;
        this.tradingPrice = tradingPrice;
        this.marketTotalAmount = marketTotalAmount;
    }

    /**
     * 직접 사용자 등록 데이터인지 확인하는 검증 메서드
     */
    public boolean isUserSourced() {
        return this.sourceType == SourceType.USER;
    }
}