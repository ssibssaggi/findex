package com.ssibssaggi.findex.client.openapi;

import java.time.LocalDate;

public record IndexDataSyncWithApiCommand(
        String indexName,
        LocalDate baseDateFrom,
        LocalDate baseDateTo
) {
    public static IndexDataSyncWithApiCommand of(
            String indexName,
            LocalDate baseDateFrom,
            LocalDate baseDateTo) {
        return new IndexDataSyncWithApiCommand(
                indexName,
                baseDateFrom,
                baseDateTo
        );
    }
}
