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

        return IndexInformationResponse.of(savedEntity);
    }

    public IndexInformationResponse findById(Long id) {
        IndexInformation entity = indexInformationService.findById(id);

        return IndexInformationResponse.of(entity);
    }

    public void deleteById(Long id) {
        indexInformationService.delete(id);
    }
}
