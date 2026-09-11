package com.ssibssaggi.findex.client.openapi;

import java.time.LocalDate;

public record IndexDataFetchQuery(
        String indexName,
        LocalDate baseDateFrom,
        LocalDate baseDateTo
) {
    public static IndexDataFetchQuery of(
            String indexName,
            LocalDate baseDateFrom,
            LocalDate baseDateTo) {
        return new IndexDataFetchQuery(
                indexName,
                baseDateFrom,
                baseDateTo
        );
    }
}
