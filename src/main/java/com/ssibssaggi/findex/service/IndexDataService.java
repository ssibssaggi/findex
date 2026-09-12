package com.ssibssaggi.findex.service;

import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexDataRepository;
import com.ssibssaggi.findex.repository.IndexInformationRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexDataService {
    private final IndexDataRepository dataRepository;
    private final IndexInformationRepository informationRepository;

    public IndexData create(IndexDataCreateRequest createRequest, SourceType sourceType) {
        Long indexInfoId = createRequest.indexInfoId();
        IndexInformation indexInformation = informationRepository.findById(indexInfoId)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "존재하지 않는 지수 정보 id: " + indexInfoId));
        LocalDate baseDate = createRequest.baseDate();

        Boolean isDuplicate = dataRepository.existsByIndexInformationAndBaseDate(
            indexInformation, baseDate);
        if (isDuplicate) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST,
                "지수 정보 id: " + indexInfoId +"번 - 정보가 존재하지 않습니다.");
        }

        IndexData indexData = IndexData.create(createRequest, indexInformation, sourceType);
        return dataRepository.save(indexData);
    }

    public IndexData findById(Long id) {
        return dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));
    }

    public IndexData update(Long id, IndexDataUpdateRequest updateRequest) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));

        IndexInformation indexInformation = informationRepository.findById(updateRequest.indexInfoId())
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                    HttpStatus.NOT_FOUND,
                    "지수 정보 id: " + updateRequest.indexInfoId() + "번 - 정보가 존재하지 않습니다."));
        indexData.update(updateRequest, indexInformation);
        return indexData;
    }

    public void delete(Long id) {
        IndexData indexData = dataRepository.findById(id)
            .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                HttpStatus.NOT_FOUND,
                "지수 데이터 id: " + id + "번 - 정보가 존재하지 않습니다."));
        dataRepository.delete(indexData);
    }
}