package com.ssibssaggi.findex.domain.entity.index;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
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
}