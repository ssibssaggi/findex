package com.ssibssaggi.findex.service;

import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexDataRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexDataService {

    private final IndexDataRepository dataRepository;
    private final com.ssibssaggi.findex.domain.service.IndexDataService indexDataService;

    public IndexData createdByOpenApi(IndexDataCreateRequest createCommand,
        IndexInformation indexInformation) {
        Long indexInfoId = createCommand.indexInfoId();

        return IndexData.create(createCommand, indexInformation, SourceType.OPEN_API);
    }

    public IndexData createdByUser(IndexDataCreateRequest createRequest,
        IndexInformation indexInformation) {
        Long indexInfoId = createRequest.indexInfoId();
        LocalDate baseDate = createRequest.baseDate();
        Boolean isDuplicate = dataRepository.existsByIndexInformationAndBaseDate(
            indexInformation, baseDate);
        if (isDuplicate) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST,
                "지수 정보 id: " + indexInfoId + "번 - 정보가 존재하지 않습니다.");
        }

        IndexData indexData = IndexData.create(createRequest, indexInformation, SourceType.USER);
        return dataRepository.save(indexData);
    }

    public IndexData findById(Long id) {
        return dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));
    }
/*
    public IndexData updatedByOpenApi(Long id, IndexDataUpdateRequest updateRequest) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.", HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));
        IndexInformation indexInformation = informationRepository.findById(indexData.getIndexInformation().getId())
                .orElseThrow()
        indexData.update(updateRequest,SourceType.OPEN_API);
    }*/

    public IndexData updatedByUser(Long id, IndexDataUpdateRequest updateRequest,
        IndexInformation indexInformation) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));

        indexData.update(updateRequest, indexInformation, SourceType.USER);
        return indexData;
    }

    public void delete(Long id) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));
        dataRepository.delete(indexData);
    }

    public void deleteByIndexInfoId(Long indexInfoId) {
        dataRepository.deleteByIndexInformationId(indexInfoId);
    }
}