package com.ssibssaggi.findex.application;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationResponse;
import com.ssibssaggi.findex.controller.dto.IndexInformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.domain.service.IndexInformationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResponse saveInformation(IndexInformationCreateRequest indexInformationCreateRequest) {
        IndexInformation savedEntity = indexInformationService.save(indexInformationCreateRequest, SourceType.USER);

        return IndexInformationResponse.of(savedEntity);
    }

    public IndexInformationResponse findById(Long id) {
        IndexInformation entity = indexInformationService.findById(id);

        return IndexInformationResponse.of(entity);
    }

    public void deleteById(Long id) {
        indexInformationService.delete(id);
    }

    public IndexInformationResponse update(
            Long id,
            IndexInformationUpdateRequest indexInformationUpdateRequest
    ) {
        IndexInformation entity = indexInformationService.update(id, indexInformationUpdateRequest);

        return IndexInformationResponse.of(entity);
    }
}
