package com.ssibssaggi.findex.domain.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.repository.IndexInformationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationService {
    private final IndexInformationRepository indexInformationRepository;

    public IndexInformation save(IndexInformationCreateRequest request, SourceType sourceType) {
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

    /**
     * Transactional 사용이유 - Transactional을사용하지않았을때 Dirty Checking가 되지않아 명시적 save를 진행해야함 사용하면 필드를 변경하면 Dirty Checking를 통해
     * 변경 감지하여 작업이 종료됨과 동시에 flush + commit을 진행해해주기 때문에 Transactional를사용함
     */
    @Transactional
    public IndexInformation update(
            Long id,
            IndexInformationUpdateRequest indexInformationUpdateRequest
    ) {
        IndexInformation entity = indexInformationRepository.findById(id)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                        HttpStatus.NOT_FOUND,
                        "수정 요청 id : " + id + "번 - 정보가 존재하지 않습니다."));

        entity.update(
                indexInformationUpdateRequest.employedItemsCount(),
                indexInformationUpdateRequest.basePointInTime(),
                indexInformationUpdateRequest.baseIndex(),
                indexInformationUpdateRequest.favorite()
        );

        return entity;
    }

    public List<IndexInformation> findAll() {
        return indexInformationRepository.findAll();
    }
}
