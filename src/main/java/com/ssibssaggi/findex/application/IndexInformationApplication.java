package com.ssibssaggi.findex.application;

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
import com.ssibssaggi.findex.domain.service.IndexInformationService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationApplication {
    private final IndexInformationService indexInformationService;

    public IndexInformationResponse saveInformation(IndexInformationCreateRequest indexInformationCreateRequest) {
        IndexInformation savedEntity = indexInformationService.save(indexInformationCreateRequest);

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

    /**
     * Transactional 사용이유 - Transactional을사용하지않았을때 Dirty Checking가 되지않아 명시적 save를 진행해야함 사용하면 필드를 변경하면 Dirty Checking를 통해
     * 변경 감지하여 작업이 종료됨과 동시에 flush + commit을 진행해해주기 때문에 Transactional를사용함
     */

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
