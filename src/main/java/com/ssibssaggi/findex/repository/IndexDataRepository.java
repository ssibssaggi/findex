package com.ssibssaggi.findex.repository;

import com.ssibssaggi.findex.domain.entity.index.IndexData;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class IndexDataRepository implements IndexDataInterfaceRepository {
    private final Map<Long, IndexData> data;

    public IndexDataRepository() {
        this.data = new HashMap<>();
    }

    @Override
    public IndexData save(IndexData indexData) {
        this.data.put(indexData.getId(), indexData);
        return this.data.get(indexData.getId());
    }

    @Override
    public Optional<IndexData> findById(Long id) {
        return Optional.ofNullable(this.data.get(id));
    }

    @Override
    public List<IndexData> findAll() {
        return this.data.values().stream().toList();
    }

    @Override
    public boolean existsByIdAndBaseDate(Long indexInfoId, Instant baseDate) {
        return this.findAll().stream().
            anyMatch(indexData ->
                indexData.getIndexInformation().getId().equals(indexInfoId) &&
                    indexData.getBaseDate().equals(baseDate));
    }

    @Override
    public void delete(Long id) {
        this.data.remove(id);
    }

    @Override
    public void deleteByInfoId(Long indexInfoId) {
        this.data.entrySet().removeIf(
            entry -> entry.getValue().getIndexInformation() != null &&
                entry.getValue().getIndexInformation().getId().equals(indexInfoId)
        );
    }
    //repo에 id 기반의 삭제 -> indexinformation 칼럼을 기준으로 삭제 기능 추가
}