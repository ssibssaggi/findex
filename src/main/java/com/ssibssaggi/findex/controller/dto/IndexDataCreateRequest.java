package com.ssibssaggi.findex.controller.dto;

import java.time.LocalDate;
import java.util.Objects;

// 지수 데이터 등록 요청 DTO
public final class IndexDataCreateRequest {

    // 등록 대상 지수의 ID
    private final Long indexInformationId;
    // 지수 날짜
    private final LocalDate baseDate;

    private final Float marketPrice;      // 시가
    private final Float closingPrice;     // 종가
    private final Float highPrice;        // 고가
    private final Float lowPrice;         // 저가
    private final Float versus;           // 전일 대비
    private final Float fluctuationRate;  // 등락률
    private final Long tradingQuantity;        // 거래량
    private final Float tradingPrice;     // 거래대금
    private final Float marketTotalAmount; // 상장 시가 총액

    // 모든 필드를 초기화하는 생성자
    public IndexDataCreateRequest(Long indexInformationId, LocalDate baseDate,
            Float marketPrice, Float closingPrice,
            Float highPrice, Float lowPrice,
            Float versus, Float fluctuationRate,
            Long tradingQuantity, Float tradingPrice,
            Float marketTotalAmount) {
        this.indexInformationId = indexInformationId;
        this.baseDate = baseDate;
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

    public Long indexInformationId() {
        return indexInformationId;
    }

    public LocalDate baseDate() {
        return baseDate;
    }

    public Float marketPrice() {
        return marketPrice;
    }

    public Float closingPrice() {
        return closingPrice;
    }

    public Float highPrice() {
        return highPrice;
    }

    public Float lowPrice() {
        return lowPrice;
    }

    public Float versus() {
        return versus;
    }

    public Float fluctuationRate() {
        return fluctuationRate;
    }

    public Long tradingQuantity() {
        return tradingQuantity;
    }

    public Float tradingPrice() {
        return tradingPrice;
    }

    public Float marketTotalAmount() {
        return marketTotalAmount;
    }

    // 모든 필드 값이 같으면 같은 객체로 취급
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        IndexDataCreateRequest that = (IndexDataCreateRequest) object;
        return Objects.equals(indexInformationId, that.indexInformationId)
                && Objects.equals(baseDate, that.baseDate)
                && Objects.equals(marketPrice, that.marketPrice)
                && Objects.equals(closingPrice, that.closingPrice)
                && Objects.equals(highPrice, that.highPrice)
                && Objects.equals(lowPrice, that.lowPrice)
                && Objects.equals(versus, that.versus)
                && Objects.equals(fluctuationRate, that.fluctuationRate)
                && Objects.equals(tradingQuantity, that.tradingQuantity)
                && Objects.equals(tradingPrice, that.tradingPrice)
                && Objects.equals(marketTotalAmount, that.marketTotalAmount);
    }

    // 로그 출력, 디버깅 시 객체 내용을 사람이 읽을 수 있는 문자열로 보여줌
    @Override
    public String toString() {
        return "IndexDataCreateRequest["
                + "indexInformationId=" + indexInformationId + ", "
                + "baseDate=" + baseDate + ", "
                + "marketPrice=" + marketPrice + ", "
                + "closingPrice=" + closingPrice + ", "
                + "highPrice=" + highPrice + ", "
                + "lowPrice=" + lowPrice + ", "
                + "versus=" + versus + ", "
                + "fluctuationRate=" + fluctuationRate + ", "
                + "tradingQuantity=" + tradingQuantity + ", "
                + "tradingPrice=" + tradingPrice + ", "
                + "marketTotalAmount=" + marketTotalAmount
                + "]";
    }
}
