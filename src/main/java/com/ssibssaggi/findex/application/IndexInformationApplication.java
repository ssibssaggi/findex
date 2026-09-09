package com.ssibssaggi.findex.application;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.controller.dto.IndexInformationRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationResponse;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.entity.index.SourceType;
import com.ssibssaggi.findex.domain.service.IndexInformationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResponse saveInformation(IndexInformationRequest indexInformationRequest) {
        IndexInformation savedEntity = indexInformationService.save(indexInformationRequest, SourceType.USER);

        return new IndexInformationResponse(savedEntity.getId(),
                savedEntity.getIndexClassification(),
                savedEntity.getIndexName(),
                savedEntity.getEmployedItemsCount(),
                savedEntity.getBasePointInTime().toString(),
                savedEntity.getBaseIndex(),
                savedEntity.getSourceType().toString(),
                savedEntity.getFavorite()
        );
    }
}
