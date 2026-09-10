package com.ssibssaggi.findex.domain.service.integrationhistory;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.application.indexintegration.InsertIntegrationHistoryCommand;
import com.ssibssaggi.findex.domain.entity.integrationhistory.IntegrationHistory;
import com.ssibssaggi.findex.repository.IntegrationHistoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IntegrationHistoryService {
    private final IntegrationHistoryRepository integrationHistoryRepository;

    public List<IntegrationHistory> insert(List<InsertIntegrationHistoryCommand> commands) {
        List<IntegrationHistory> creating = commands.stream()
                .map(command -> IntegrationHistory.createIndexInformationHistory(
                        command.worker(),
                        command.indexInformation()
                ))
                .toList();
        return integrationHistoryRepository.saveAll(creating);
    }
}
