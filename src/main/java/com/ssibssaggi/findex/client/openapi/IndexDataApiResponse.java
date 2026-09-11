package com.ssibssaggi.findex.client.openapi;

import java.util.List;

public record IndexDataApiResponse(Response response) {

    public record Response(Body body) {
    }

    public record Body(Items items) {
    }

    public record Items(List<IndexDataApiItem> item) {
    }

    public List<IndexDataApiItem> toIndexDataApiItems() {
        return response().body().items().item();
    }
}
