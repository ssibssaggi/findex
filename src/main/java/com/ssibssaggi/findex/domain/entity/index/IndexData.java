package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 지수 데이터 엔티티 클래스입니다. 일자별 지수 수치 정보(시가, 종가, 고가, 저가, 거래량 등)를 관리하며, 동일 지수 정보 ID와 기준일자(baseDate) 조합에 대해 유니크 제약 조건을 가집니다.
 */

//특정 날짜의 지수 수치
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        name = "index_data",
        uniqueConstraints = @UniqueConstraint(columnNames = {"index_information_id", "base_date"})
)
public class IndexData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "index_information_id", updatable = false)
    private IndexInformation indexInformation;

    @Column(updatable = false)
    private LocalDate baseDate;

    @Enumerated(EnumType.STRING)
    private SourceType sourceType;

    private Float marketPrice;
    private Float closingPrice;
    private Float highPrice;
    private Float lowPrice;
    private Float versus;
    private Float fluctuationRate;
    private Long tradingQuantity;
    private Float tradingPrice;
    private Float marketTotalAmount;

    @Builder
    public IndexData(IndexInformation indexInformation, LocalDate baseDate, SourceType sourceType,
            Float marketPrice, Float closingPrice, Float highPrice,
            Float lowPrice, Float versus, Float fluctuationRate,
            Long tradingQuantity, Float tradingPrice, Float marketTotalAmount) {
        this.indexInformation = indexInformation;
        this.baseDate = baseDate;
        this.sourceType = sourceType;
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
     * 지수, 날짜, 출처를 제외한 수치 정보 필드 항목을 수정합니다. 엔티티 상태를 직접 변경하여 JPA Dirty Checking(변경 감지)에 의해 수정사항이 DB에 반영되도록 합니다.
     */

    public void update(Float marketPrice, Float closingPrice, Float highPrice,
            Float lowPrice, Float versus, Float fluctuationRate,
            Long tradingQuantity, Float tradingPrice, Float marketTotalAmount) {
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
    
    public boolean isUserSourced() {
        return this.sourceType == SourceType.USER;
    }
}
