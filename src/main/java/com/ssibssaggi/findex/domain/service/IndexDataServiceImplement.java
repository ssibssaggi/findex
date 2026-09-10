package com.ssibssaggi.findex.domain.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.controller.dto.IndexDataExportResponse;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.repository.IndexDataExportRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexDataServiceImplement implements IndexDataService {

    private final IndexDataExportRepository indexDataExportRepository;

    @Override
    public List<IndexDataExportResponse> findAllForExport(Long indexInformationId,
            LocalDate startDate,
            LocalDate endDate) {
        return indexDataExportRepository
                .findByIndexInformationIdAndBaseDateBetween(indexInformationId, startDate, endDate)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private IndexDataExportResponse toResponse(IndexData data) {
        return new IndexDataExportResponse(
                data.getBaseDate(),
                data.getMarketPrice(),
                data.getClosingPrice(),
                data.getHighPrice(),
                data.getLowPrice(),
                data.getVersus(),
                data.getFluctuationRate(),
                data.getTradingQuantity(),
                data.getTradingPrice(),
                data.getMarketTotalAmount()
        );
    }
}
