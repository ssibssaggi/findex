package com.ssibssaggi.findex.controller.dto;

import java.util.Objects;

// 지수 데이터 수정 요청 DTO
public final class IndexDataUpdateRequest {

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
    public IndexDataUpdateRequest(Float marketPrice, Float closingPrice,
            Float highPrice, Float lowPrice,
            Float versus, Float fluctuationRate,
            Long tradingQuantity, Float tradingPrice,
            Float marketTotalAmount) {
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        IndexDataUpdateRequest that = (IndexDataUpdateRequest) object;
        return Objects.equals(marketPrice, that.marketPrice)
                && Objects.equals(closingPrice, that.closingPrice)
                && Objects.equals(highPrice, that.highPrice)
                && Objects.equals(lowPrice, that.lowPrice)
                && Objects.equals(versus, that.versus)
                && Objects.equals(fluctuationRate, that.fluctuationRate)
                && Objects.equals(tradingQuantity, that.tradingQuantity)
                && Objects.equals(tradingPrice, that.tradingPrice)
                && Objects.equals(marketTotalAmount, that.marketTotalAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(marketPrice, closingPrice, highPrice, lowPrice,
                versus, fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);
    }

    // 로그 출력
    @Override
    public String toString() {
        return "IndexDataUpdateRequest["
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
