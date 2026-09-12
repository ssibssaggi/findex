package com.ssibssaggi.findex.application.index;

import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataResponse;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.service.index.IndexInformationService;
import com.ssibssaggi.findex.service.IndexDataService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexDataApplication {

    private final IndexDataService indexDataService;
    private final IndexInformationService indexInformationService;

    @Transactional
    public IndexDataResponse saveData(IndexDataCreateRequest createRequest) {
        Long indexInfoId = createRequest.indexInfoId();
        IndexInformation indexInformation = indexInformationService.findById(indexInfoId);
        IndexData savedIndexData = indexDataService.createdByUser(createRequest, indexInformation);

        return IndexDataResponse.toDto(savedIndexData);
    }

    public IndexDataResponse findById(Long id) {
        IndexData indexData = indexDataService.findById(id);

        return IndexDataResponse.toDto(indexData);
    }

    @Transactional
    public void delete(Long id) {
        indexDataService.delete(id);
    }

    /*
    @Transactional
    public void deletedByInfoId(Long indexInfoId) {

    }*/
}