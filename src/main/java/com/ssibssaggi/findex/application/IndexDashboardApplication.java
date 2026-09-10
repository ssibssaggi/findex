package com.ssibssaggi.findex.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.ChartPeriodType;
import com.ssibssaggi.findex.controller.dto.IndexChartResponse;
import com.ssibssaggi.findex.controller.dto.IndexPerformanceRankResponse;
import com.ssibssaggi.findex.controller.dto.IndexPerformanceSummaryResponse;
import com.ssibssaggi.findex.controller.dto.RankPeriodType;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.repository.IndexDataRepository;
import com.ssibssaggi.findex.repository.IndexInformationJpaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndexDashboardApplication {

    private final IndexDataRepository indexDataRepository;
    private final IndexInformationJpaRepository indexInformationJpaRepository;

    // 1. 주요 지수 현황 요약
    public List<IndexPerformanceSummaryResponse> getFavoriteIndexSummary() {
        List<IndexInformation> favorites =
                indexInformationJpaRepository.findByFavoriteTrueAndEnabledTrue();

        List<IndexPerformanceSummaryResponse> favoriteIndexSummaries = new ArrayList<>();
        for (IndexInformation indexInformation : favorites) {
            indexDataRepository.findTopByIndexInformationIdOrderByBaseDateDesc(indexInformation.getId())
                    .ifPresent(latestIndexData -> favoriteIndexSummaries.add(new IndexPerformanceSummaryResponse(
                            indexInformation.getId(),
                            indexInformation.getIndexClassification(),
                            indexInformation.getIndexName(),
                            latestIndexData.getBaseDate(),
                            latestIndexData.getClosingPrice(),
                            latestIndexData.getVersus(),
                            latestIndexData.getFluctuationRate()
                    )));
        }
        return favoriteIndexSummaries;
    }

    //2. 지수 차트
    public IndexChartResponse getChart(Long indexInformationId, ChartPeriodType periodType) {
        IndexInformation indexInformation = indexInformationJpaRepository.findById(indexInformationId)
                .orElseThrow(() -> new CustomException("", HttpStatus.NOT_FOUND,
                        "지수 정보를 찾을 수 없습니다. id=" + indexInformationId));

        LocalDate toDate = LocalDate.now();
        LocalDate fromDate = switch (periodType) {
            case MONTHLY -> toDate.minusMonths(1);
            case QUARTERLY -> toDate.minusMonths(3);
            case YEARLY -> toDate.minusYears(1);
        };

        // 이동평균(최대 20일) 계산을 위해 조회 시작일보다 20일 더 앞선 데이터까지 함께 조회
        LocalDate fetchFromDate = fromDate.minusDays(20);
        List<IndexData> dailyData = indexDataRepository
                .findByIndexInformationIdAndBaseDateBetweenOrderByBaseDateAsc(
                        indexInformationId, fetchFromDate, toDate);

        List<IndexChartResponse.ChartPoint> chartPoints = new ArrayList<>();
        for (int i = 0; i < dailyData.size(); i++) {
            IndexData current = dailyData.get(i);
            if (current.getBaseDate().isBefore(fromDate)) {
                continue; // 이동평균 계산용 데이터일 뿐, 응답 구간에는 포함하지 않음
            }
            chartPoints.add(new IndexChartResponse.ChartPoint(
                    current.getBaseDate(),
                    current.getClosingPrice(),
                    calculateMovingAverage(dailyData, i, 5),
                    calculateMovingAverage(dailyData, i, 20)
            ));
        }

        return new IndexChartResponse(indexInformation.getId(),
                indexInformation.getIndexName(),
                periodType,
                chartPoints);
    }

    // idx번째 데이터를 기준으로 직전 n일 종가 평균 계산 (n일치 데이터가 없으면 null)
    private Float calculateMovingAverage(List<IndexData> dailyData, int currentIndex, int windowSize) {
        if (currentIndex + 1 < windowSize) {
            return null;
        }
        float sum = 0.0F;
        for (int i = currentIndex - windowSize + 1; i <= currentIndex; i++) {
            sum += dailyData.get(i).getClosingPrice();
        }
        return sum / windowSize;
    }

    // ── 3. 지수 성과 분석 랭킹
    public List<IndexPerformanceRankResponse> getPerformanceRank(RankPeriodType periodType) {
        List<IndexInformation> enabledIndexInformations = indexInformationJpaRepository.findByEnabledTrue();

        LocalDate today = LocalDate.now();
        LocalDate compareDate = switch (periodType) {
            case DAY -> today.minusDays(1);
            case WEEK -> today.minusWeeks(1);
            case MONTH -> today.minusMonths(1);
        };

        List<IndexPerformanceRankResponse> unrankedPerformanceRanks = new ArrayList<>();
        for (IndexInformation indexInformation : enabledIndexInformations) {
            Optional<IndexData> latestIndexDataOptional =
                    indexDataRepository.findTopByIndexInformationIdOrderByBaseDateDesc(indexInformation.getId());
            if (latestIndexDataOptional.isEmpty()) {
                continue;
            }
            IndexData latestIndexData = latestIndexDataOptional.get();

            Optional<IndexData> comparisonIndexDataOptional = indexDataRepository
                    .findTopByIndexInformationIdAndBaseDateLessThanEqualOrderByBaseDateDesc(
                            indexInformation.getId(), compareDate);
            if (comparisonIndexDataOptional.isEmpty()) {
                continue; // 비교 시점 데이터가 없으면 랭킹 계산 불가 → 제외
            }
            IndexData comparisonIndexData = comparisonIndexDataOptional.get();

            float versus = latestIndexData.getClosingPrice() - comparisonIndexData.getClosingPrice();
            float fluctuationRate = comparisonIndexData.getClosingPrice() == 0.0F
                    ? 0.0F
                    : versus / comparisonIndexData.getClosingPrice() * 100.0F;

            unrankedPerformanceRanks.add(new IndexPerformanceRankResponse(
                    0, indexInformation.getId(), indexInformation.getIndexName(),
                    latestIndexData.getBaseDate(), latestIndexData.getClosingPrice(),
                    comparisonIndexData.getBaseDate(), comparisonIndexData.getClosingPrice(),
                    versus, fluctuationRate
            ));
        }

        // 등락률 내림차순 정렬 후 순위 부여
        unrankedPerformanceRanks.sort((firstRank, secondRank) ->
                Float.compare(secondRank.fluctuationRate(), firstRank.fluctuationRate()));

        List<IndexPerformanceRankResponse> ranked = new ArrayList<>();
        for (int i = 0; i < unrankedPerformanceRanks.size(); i++) {
            IndexPerformanceRankResponse performanceRank = unrankedPerformanceRanks.get(i);
            ranked.add(new IndexPerformanceRankResponse(
                    i + 1, performanceRank.indexInformationId(), performanceRank.indexName(),
                    performanceRank.baseDate(), performanceRank.closingPrice(),
                    performanceRank.beforeBaseDate(), performanceRank.beforeClosingPrice(),
                    performanceRank.versus(), performanceRank.fluctuationRate()
            ));
        }
        return ranked;
    }
}
