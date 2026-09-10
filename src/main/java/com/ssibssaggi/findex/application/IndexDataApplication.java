package com.ssibssaggi.findex.application;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssibssaggi.findex.common.exception.FindexException;
import com.ssibssaggi.findex.common.exception.FindexException.ErrorCode;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexDataRepository;
import com.ssibssaggi.findex.repository.IndexInformationJpaRepository;

import lombok.RequiredArgsConstructor;

import static com.ssibssaggi.findex.repository.IndexDataSpecification.createBaseDateFromSpecification;
import static com.ssibssaggi.findex.repository.IndexDataSpecification.createBaseDateToSpecification;
import static com.ssibssaggi.findex.repository.IndexDataSpecification.createIndexInformationIdSpecification;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IndexDataApplication {

    private final IndexDataRepository indexDataRepository;
    private final IndexInformationJpaRepository indexInformationRepository;

    @Transactional
    public IndexData register(Long indexInformationId, LocalDate baseDate, SourceType sourceType,
            Float marketPrice, Float closingPrice, Float highPrice,
            Float lowPrice, Float versus, Float fluctuationRate,
            Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) { // Integer -> Long 변경

        // 1. ID 기반으로 엔티티 조회
        IndexInformation indexInformation = indexInformationRepository.findById(indexInformationId)
                .orElseThrow(() -> new FindexException(
                        ErrorCode.INDEX_INFORMATION_NOT_FOUND,
                        "지수 정보를 찾을 수 없습니다. id=" + indexInformationId));

        // 2. 지수+날짜 중복 검증
        validateIndexDataDoesNotExist(indexInformationId, baseDate);

        IndexData indexData = IndexData.builder()
                .indexInformation(indexInformation)
                .baseDate(baseDate)
                .sourceType(sourceType != null ? sourceType : SourceType.USER)
                .marketPrice(marketPrice)
                .closingPrice(closingPrice)
                .highPrice(highPrice)
                .lowPrice(lowPrice)
                .versus(versus)
                .fluctuationRate(fluctuationRate)
                .tradingQuantity(tradingQuantity)
                .tradingPrice(tradingPrice)
                .marketTotalAmount(marketTotalAmount)
                .build();

        return indexDataRepository.save(indexData);
    }

    // 수정: 지수, 날짜를 제외한 속성만 변경 가능
    @Transactional
    public IndexData update(Long id, Float marketPrice, Float closingPrice, Float highPrice,
            Float lowPrice, Float versus, Float fluctuationRate,
            Long tradingQuantity, Long tradingPrice, Long marketTotalAmount) { // Integer -> Long 변경

        IndexData indexData = indexDataRepository.findById(id)
                .orElseThrow(() -> new FindexException(
                        ErrorCode.INDEX_DATA_NOT_FOUND,
                        "지수 데이터를 찾을 수 없습니다. id=" + id));

        indexData.update(marketPrice, closingPrice, highPrice, lowPrice, versus,
                fluctuationRate, tradingQuantity, tradingPrice, marketTotalAmount);

        return indexData;
    }

    @Transactional
    public void delete(Long id) {
        IndexData indexData = indexDataRepository.findById(id)
                .orElseThrow(() -> new FindexException(
                        ErrorCode.INDEX_DATA_NOT_FOUND,
                        "지수 데이터를 찾을 수 없습니다. id=" + id));

        if (!indexData.isUserSourced()) {
            throw new FindexException(
                    ErrorCode.INDEX_DATA_DELETION_FORBIDDEN,
                    "사용자가 직접 등록한 데이터만 삭제할 수 있습니다. id=" + id);
        }

        indexDataRepository.delete(indexData);
    }

    // 목록 조회: 조건이 여러 개면 모두 만족(AND)하는 결과만 조회
    public List<IndexData> findIndexDataList(Long indexInformationId,
            LocalDate fromDate,
            LocalDate toDate,
            Pageable pageable) {
        Specification<IndexData> indexDataSpecification = Specification
                .where(createIndexInformationIdSpecification(indexInformationId))
                .and(createBaseDateFromSpecification(fromDate))
                .and(createBaseDateToSpecification(toDate));

        return indexDataRepository.findAll(indexDataSpecification, pageable).getContent();
    }

    private void validateIndexDataDoesNotExist(Long indexInformationId, LocalDate baseDate) {
        if (indexDataRepository.existsByIndexInformationIdAndBaseDate(indexInformationId, baseDate)) {
            throw new FindexException(
                    ErrorCode.INDEX_DATA_DUPLICATE,
                    "이미 존재하는 지수 데이터입니다. indexInformationId=" + indexInformationId
                            + ", baseDate=" + baseDate);
        }
    }
}