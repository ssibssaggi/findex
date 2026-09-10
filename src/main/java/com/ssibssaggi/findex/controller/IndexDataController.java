package com.ssibssaggi.findex.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssibssaggi.findex.application.IndexDashboardApplication;
import com.ssibssaggi.findex.application.IndexDataApplication;
import com.ssibssaggi.findex.controller.dto.ChartPeriodType;
import com.ssibssaggi.findex.controller.dto.IndexChartResponse;
import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataResponse;
import com.ssibssaggi.findex.controller.dto.IndexDataUpdateRequest;
import com.ssibssaggi.findex.controller.dto.IndexPerformanceRankResponse;
import com.ssibssaggi.findex.controller.dto.IndexPerformanceSummaryResponse;
import com.ssibssaggi.findex.controller.dto.RankPeriodType;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.SourceType;

import lombok.RequiredArgsConstructor;

// 지수 데이터(IndexData) 관련 API 엔드포인트를 담당하는 컨트롤러
// 요청을 받아 서비스에 위임하고, 그 결과를 HTTP 응답으로 변환하는 역할만 수행
@RestController
@RequiredArgsConstructor
public class IndexDataController {

    private final IndexDataApplication indexDataApplication;
    private final IndexDashboardApplication indexDashboardApplication;

    // [등록] POST /api/index-data
    @PostMapping("/api/index-data")
    public ResponseEntity<IndexDataResponse> register(@RequestBody IndexDataCreateRequest request) {
        IndexData created = indexDataApplication.register(
                request.indexInformationId(), request.baseDate(), SourceType.USER,
                request.marketPrice(), request.closingPrice(), request.highPrice(), request.lowPrice(),
                request.versus(), request.fluctuationRate(), request.tradingQuantity(),
                request.tradingPrice(), request.marketTotalAmount()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body((IndexDataResponse.fromIndexData(created)));
    }

    // [수정] PATCH /api/index-data/{indexDataId}
    @PatchMapping("/api/index-data/{indexDataId}")
    public ResponseEntity<IndexDataResponse> update(@PathVariable("indexDataId") Long indexDataId,
            @RequestBody IndexDataUpdateRequest request) {
        IndexData updated = indexDataApplication.update(
                indexDataId, request.marketPrice(), request.closingPrice(), request.highPrice(), request.lowPrice(),
                request.versus(), request.fluctuationRate(), request.tradingQuantity(),
                request.tradingPrice(), request.marketTotalAmount()
        );
        return ResponseEntity.status(HttpStatus.CREATED)
                .body((IndexDataResponse.fromIndexData(updated)));
    }

    // [삭제] DELETE /api/index-data/{indexDataId}
    @DeleteMapping("/api/index-data/{indexDataId}")
    public ResponseEntity<Void> delete(@PathVariable("indexDataId") Long indexDataId) {
        indexDataApplication.delete(indexDataId);
        return ResponseEntity.noContent().build();
    }

    // [목록 조회] GET /api/index-data
    @GetMapping("/api/index-data")
    public ResponseEntity<List<IndexDataResponse>> getIndexDataList(
            @RequestParam(required = false) Long indexInformationId,
            @RequestParam(required = false) LocalDate fromDate,
            @RequestParam(required = false) LocalDate toDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        List<IndexDataResponse> indexDataResponses = indexDataApplication.findIndexDataList(
                        indexInformationId, fromDate, toDate, PageRequest.of(page, size)
                ).stream()
                .map(IndexDataResponse::fromIndexData)
                .toList();
        return ResponseEntity.ok(indexDataResponses);
    }

    @GetMapping("/api/index-informations/favorites/performance-summaries")
    public ResponseEntity<List<IndexPerformanceSummaryResponse>> getFavoriteSummary() {
        return ResponseEntity.ok(indexDashboardApplication.getFavoriteIndexSummary());
    }

    // [지수 차트 조회] GET /api/index-informations/{indexInformationId}/chart-points
    @GetMapping("/api/index-informations/{indexInformationId}/chart-points")
    public ResponseEntity<IndexChartResponse> getChart(
            @PathVariable("indexInformationId") Long indexInformationId,
            @RequestParam ChartPeriodType periodType) {
        return ResponseEntity.ok(
                indexDashboardApplication.getChart(indexInformationId, periodType));
    }

    // [지수 성과 랭킹 조회] GET /api/index-informations/performance-rankings
    @GetMapping("/api/index-informations/performance-rankings")
    public ResponseEntity<List<IndexPerformanceRankResponse>> getPerformanceRank(
            @RequestParam RankPeriodType periodType) {
        return ResponseEntity.ok(indexDashboardApplication.getPerformanceRank(periodType));
    }
}
