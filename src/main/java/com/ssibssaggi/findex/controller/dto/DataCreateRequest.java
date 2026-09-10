package com.ssibssaggi.findex.controller.dto;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import lombok.Getter;


@Getter
public class DataCreateRequest {
    @NotNull
    Long indexInfoId;
    @NotNull
    Instant baseDate;
    @NotNull
    BigDecimal marketPrice;
    @NotNull
    BigDecimal closingPrice;
    @NotNull
    BigDecimal highPrice;
    @NotNull
    BigDecimal lowPrice;
    @NotNull
    BigDecimal versus;
    @NotNull
    BigDecimal fluctuationRate;
    @NotNull
    Long tradingQuantity;
    @NotNull
    Long tradingPrice;
    @NotNull
    Long marketTotalAmount;
}