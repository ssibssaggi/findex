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
}
