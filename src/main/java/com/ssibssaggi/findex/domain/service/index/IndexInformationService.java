package com.ssibssaggi.findex.domain.service.index;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.application.indexintegration.UpsertIndexInformationCommand;
import com.ssibssaggi.findex.common.dto.PageResponse;
import com.ssibssaggi.findex.common.exception.CustomException;
import com.ssibssaggi.findex.controller.dto.CursorPageCondition;
import com.ssibssaggi.findex.controller.dto.IndexInfoSearchCondition;
import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;
import com.ssibssaggi.findex.repository.IndexInformationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationService {

    private final IndexInformationRepository indexInformationRepository;

    public List<IndexInformation> upsertInformationByIndexClassificationAndIndexName(
            List<UpsertIndexInformationCommand> commands
    ) {
        List<IndexInformation> indexInformations = commands.stream()
                .map(this::upsertInformation)
                .toList();

        return indexInformationRepository.saveAll(indexInformations);
    }

    private IndexInformation upsertInformation(UpsertIndexInformationCommand command) {
        return indexInformationRepository
                .findByIndexClassificationAndIndexName(
                        command.indexClassification(),
                        command.indexName()
                )
                .map(indexInformation -> updateInformation(indexInformation, command))
                .orElseGet(() -> createInformation(command));
    }

    private IndexInformation updateInformation(
            IndexInformation indexInformation,
            UpsertIndexInformationCommand command
    ) {
        indexInformation.updateWithOpenApi(
                command.employedItemCount(),
                command.basePointInTime(),
                command.baseIndex()
        );
        return indexInformation;
    }

    private IndexInformation createInformation(UpsertIndexInformationCommand command) {
        return IndexInformation.createWithOpenApi(
                command.indexClassification(),
                command.indexName(),
                command.employedItemCount(),
                command.basePointInTime(),
                command.baseIndex()
        );
    }

    public IndexInformation createInformation(IndexInformationCreateRequest request) {
        boolean isDuplicate = this.validateByIndexClassificationAndIndexName(request.indexClassification(),
                request.indexName());

        if (isDuplicate) {
            throw new CustomException("잘못된 요청입니다.", HttpStatus.BAD_REQUEST, "동일한 지수 분류와 지수명이 이미 등록되어 있습니다.");
        }

        IndexInformation entity = IndexInformation.createWithUser(request);
        return indexInformationRepository.save(entity);
    }

    private boolean validateByIndexClassificationAndIndexName(String indexClassification, String indexName) {
        return indexInformationRepository.existsByIndexClassificationAndIndexName(
                indexClassification,
                indexName
        );
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

    public IndexInformation update(
            Long id,
            IndexInformationUpdateRequest indexInformationUpdateRequest
    ) {
        IndexInformation entity = indexInformationRepository.findById(id)
                .orElseThrow(() -> new CustomException("잘못된 요청입니다.",
                        HttpStatus.NOT_FOUND,
                        "수정 요청 id : " + id + "번 - 정보가 존재하지 않습니다."));

        entity.updateWithUser(
                indexInformationUpdateRequest.employedItemsCount(),
                indexInformationUpdateRequest.basePointInTime(),
                indexInformationUpdateRequest.baseIndex(),
                indexInformationUpdateRequest.favorite()
        );

        return entity;
    }

    public List<IndexInformation> findSummary() {
        return indexInformationRepository.findAll();
    }

    public PageResponse<IndexInformation> searchIndexInfos(
            IndexInfoSearchCondition indexInfoSearchCondition,
            CursorPageCondition cursorPageCondition
    ) {
        List<IndexInformation> entities = indexInformationRepository.searchIndexInfos(indexInfoSearchCondition,
                cursorPageCondition);
        Long totalElements = indexInformationRepository.count(indexInfoSearchCondition);

        Long nextIdAfter = null;
        String nextCursor = null;
        Boolean hashNext = entities.size() > cursorPageCondition.size();

        List<IndexInformation> content = entities.subList(0, Math.min(entities.size(), cursorPageCondition.size()));

        if (!entities.isEmpty()) {
            IndexInformation lastEntity = entities.get(content.size() - 1);
            nextIdAfter = lastEntity.getId();
            nextCursor = this.getLastSortValue(cursorPageCondition.sortField(), lastEntity);
        }

        return PageResponse.of(content, nextCursor, nextIdAfter, totalElements, cursorPageCondition.size(), hashNext);
    }

    private String getLastSortValue(
            String sortField,
            IndexInformation indexInformation
    ) {
        return switch (sortField) {
            case "indexClassification" -> indexInformation.getIndexClassification();
            case "indexName" -> indexInformation.getIndexName();
            case "employedItemsCount" -> indexInformation.getEmployedItemsCount().toString();
            default -> null;
        };
    }
}
