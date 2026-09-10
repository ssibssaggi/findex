package com.ssibssaggi.findex.domain.service.index;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.application.indexintegration.UpsertIndexInformationCommand;
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
                .findIndexInformationByIndexClassificationAndIndexName(
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
}
