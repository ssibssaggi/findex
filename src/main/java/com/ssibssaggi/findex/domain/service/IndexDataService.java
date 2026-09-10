package com.ssibssaggi.findex.domain.service;

import java.time.LocalDate;
import java.util.List;

import com.ssibssaggi.findex.controller.dto.IndexDataExportResponse;

public interface IndexDataService {
    List<IndexDataExportResponse> findAllForExport(
            Long indexInformationId,
            LocalDate startDate,
            LocalDate endDate
    );
}
