package com.ssibssaggi.findex.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssibssaggi.findex.domain.entity.index.IndexData;

public interface IndexDataExportRepository extends JpaRepository<IndexData, Long> {

    List<IndexData> findByIndexInformationIdAndBaseDateBetween(
            Long indexInformationId, LocalDate startDate, LocalDate endDate
    );
}
