package com.ssibssaggi.findex.application.indexintegration;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssibssaggi.findex.client.openapi.IndexOpenApiClient;
import com.ssibssaggi.findex.controller.dto.SyncJobDto;
import com.ssibssaggi.findex.domain.entity.integrationhistory.IntegrationHistory;
import com.ssibssaggi.findex.domain.service.index.IndexInformationService;
import com.ssibssaggi.findex.domain.service.integrationhistory.IntegrationHistoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexIntegrationApplication {
    private final IndexOpenApiClient indexOpenApiClient;
    private final IndexInformationService indexInformationService;
    private final IntegrationHistoryService integrationHistoryService;

    @Transactional
    public List<SyncJobDto> syncIndexInfoWithOpenApi(String worker) {
        List<UpsertIndexInformationCommand> upsertIndexInformationCommands = indexOpenApiClient.syncIndexInformation()
                .stream()
                .map(UpsertIndexInformationCommand::from)
                .toList();
        List<InsertIntegrationHistoryCommand> insertIntegrationHistoryCommands = indexInformationService
                .upsertInformationByIndexClassificationAndIndexName(upsertIndexInformationCommands).stream()
                .map(indexInformation -> InsertIntegrationHistoryCommand.of(worker, indexInformation))
                .toList();
        List<IntegrationHistory> integrationHistories = integrationHistoryService.insert(
                insertIntegrationHistoryCommands);
        return SyncJobDto.from(integrationHistories);
    }
}