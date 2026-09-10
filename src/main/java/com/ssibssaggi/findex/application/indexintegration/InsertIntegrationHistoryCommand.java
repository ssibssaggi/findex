package com.ssibssaggi.findex.application.indexintegration;

import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public record InsertIntegrationHistoryCommand(
        String worker,
        IndexInformation indexInformation
) {

    public static InsertIntegrationHistoryCommand of(String worker, IndexInformation indexInformation) {
        return new InsertIntegrationHistoryCommand(
                worker,
                indexInformation
        );
    }
}
