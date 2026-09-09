package com.ssibssaggi.findex.domain.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.IndexInformationRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexInformationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationService {
    private final IndexInformationRepository indexInformationRepository;

    public IndexInformation save(IndexInformationRequest request, SourceType sourceType) {
        boolean isDuplicate = this.validateByIndexClassificationAndIndexName(request.indexClassification(),
                request.indexName());

        if (isDuplicate) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST, "동일한 지수 분류와 지수명이 이미 등록되어 있습니다.");
        }

        IndexInformation entity = IndexInformation.create(request, sourceType);
        return indexInformationRepository.save(entity);
    }

    private boolean validateByIndexClassificationAndIndexName(String indexClassification, String indexName) {
        return indexInformationRepository.findByIndexClassificationAndIndexName(
                indexClassification,
                indexName).isPresent();
    }

    public IndexInformation findById(Long id) {
        return indexInformationRepository.findById(id)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                        HttpStatus.NOT_FOUND,
                        "요청 id : " + id + "번 - 정보가 존재하지 않습니다."));
    }

    public void delete(Long id) {
        IndexInformation entity = indexInformationRepository.findById(id)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                        HttpStatus.NOT_FOUND,
                        "삭제 요청 id : " + id + "번 - 정보가 존재하지 않습니다."));
        indexInformationRepository.delete(entity);
    }
}
