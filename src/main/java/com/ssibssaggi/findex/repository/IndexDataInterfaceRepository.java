package com.ssibssaggi.findex.repository;

import com.ssibssaggi.findex.domain.entity.index.IndexData;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface IndexDataInterfaceRepository {
    IndexData save(IndexData indexData);
    Optional<IndexData> findById(Long id);
    List<IndexData> findAll();
    boolean existsByIdAndBaseDate(Long indexInfoId, Instant baseDate);
    void delete(Long id);
    void deleteByInfoId(Long indexInfoId);
}