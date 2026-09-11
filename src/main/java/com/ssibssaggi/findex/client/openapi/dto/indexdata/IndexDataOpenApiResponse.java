package com.ssibssaggi.findex.client.openapi.dto.indexdata;

import java.util.List;

public record IndexDataOpenApiResponse(Response response) {

    public record Response(Body body) {
    }

    public record Body(Items items) {
    }

    public record Items(List<IndexDataFetchResult> item) {
    }

    public List<IndexDataFetchResult> toIndexDataApiItems() {
        return response().body().items().item();
    }
}
