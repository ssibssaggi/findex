package com.ssibssaggi.findex.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ssibssaggi.findex.domain.entity.index.IndexData;

//Spring Data JPA의 메서드명 규칙을 이용합니다.
public interface IndexDataRepository
        extends JpaRepository<IndexData, Long>, JpaSpecificationExecutor<IndexData> {

    boolean existsByIndexInformationIdAndBaseDate(Long indexInformationId, LocalDate baseDate);

    // 특정 지수의 가장 최근 데이터 1건 (요약/랭킹의 '현재값' 조회용)
    Optional<IndexData> findTopByIndexInformationIdOrderByBaseDateDesc(Long indexInformationId);

    // 특정 지수의 기준일 이전(포함) 가장 최근 데이터 1건 (랭킹의 '비교값' 조회용)
    Optional<IndexData> findTopByIndexInformationIdAndBaseDateLessThanEqualOrderByBaseDateDesc(
            Long indexInformationId, LocalDate baseDate);

    // 특정 지수의 기간 내 일별 데이터 (차트/이동평균 계산용)
    List<IndexData> findByIndexInformationIdAndBaseDateBetweenOrderByBaseDateAsc(
            Long indexInformationId, LocalDate fromDate, LocalDate toDate);
}