package com.ssibssaggi.findex.application.index;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssibssaggi.findex.common.dto.PageResponse;
import com.ssibssaggi.findex.controller.dto.CursorPageCondition;
import com.ssibssaggi.findex.controller.dto.IndexInfoSearchCondition;
import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationResponse;
import com.ssibssaggi.findex.controller.dto.IndexInformationSummaryResponse;
import com.ssibssaggi.findex.controller.dto.IndexInformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.domain.service.index.IndexInformationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResponse saveInformation(IndexInformationCreateRequest indexInformationCreateRequest) {
        IndexInformation savedEntity = indexInformationService.createInformation(indexInformationCreateRequest);

        return IndexInformationResponse.of(savedEntity);
    }

    public IndexInformationResponse findById(Long id) {
        IndexInformation entity = indexInformationService.findById(id);

        return IndexInformationResponse.of(entity);
    }

    @Transactional
    public void deleteById(Long id) {
        // TODO : IndexData에서 index_information_id 체크하여 삭제하는 로직 추가 필요 (
        // JPA mappedBy 사용 시 N+1발생하여 서비스 단에서 처리하는것으로 선택)
        indexInformationService.delete(id);
    }

    @Transactional
    public IndexInformationResponse update(
            Long id,
            IndexInformationUpdateRequest indexInformationUpdateRequest
    ) {
        IndexInformation entity = indexInformationService.update(id, indexInformationUpdateRequest);
        return IndexInformationResponse.of(entity);
    }

    public List<IndexInformationSummaryResponse> findSummary() {
        return indexInformationService.findSummary().stream()
                .map(IndexInformationSummaryResponse::of)
                .toList();
    }

    public PageResponse<IndexInformationResponse> searchIndexInfos(
            IndexInfoSearchCondition indexInfoSearchCondition,
            CursorPageCondition cursorPageCondition
    ) {

        PageResponse<IndexInformation> pageEntities = indexInformationService.searchIndexInfos(
                indexInfoSearchCondition,
                cursorPageCondition
        );

        return pageEntities.map(IndexInformationResponse::of);
    }
}
