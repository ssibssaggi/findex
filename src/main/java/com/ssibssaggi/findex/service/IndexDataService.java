package com.ssibssaggi.findex.service;

import com.ssibssaggi.findex.controller.dto.DataCreateRequest;
import com.ssibssaggi.findex.controller.dto.DataUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexDataRepository;
import com.ssibssaggi.findex.repository.IndexInformationRepository;
import java.time.Instant;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexDataService implements IndexDataInterfaceService {
    private final IndexDataRepository dataRepository;
    private final IndexInformationRepository informationRepository;

    @Override
    public IndexData userCreate(DataCreateRequest createRequest) {
        Long indexInfoId = createRequest.getIndexInfoId();
        IndexInformation indexInformation = informationRepository.findById(indexInfoId)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 지수 정보 ID: " + indexInfoId));
        Instant baseDate = createRequest.getBaseDate();

        if(dataRepository.existsByIdAndBaseDate(indexInfoId, baseDate)) {
            throw new IllegalArgumentException(String.format("중복된 지수 ID와 날짜값: %d, %s", indexInfoId, baseDate));
        }

        IndexData indexData = IndexData.create(createRequest, SourceType.USER, indexInformation);
        return dataRepository.save(indexData);
    }

    @Override
    public IndexData read(Long id) {
        return dataRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 지수 데이터 id: " + id));
    }
    @Override
    public IndexData update(Long id, DataUpdateRequest dataUpdateRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("존재하지 않는 지수 데이터 id: " + id));
        dataRepository.delete(id);
    }
}