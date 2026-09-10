package com.ssibssaggi.findex.client.openapi;

import java.util.List;

public record IndexInfoApiResponse(Response response) {

    public record Response(Body body) {
    }

    public record Body(Items items) {
    }

    public record Items(List<IndexInfoApiItem> item) {
    }

    public List<IndexInfoApiItem> toIndexInfoApiItem() {
        return response().body().items().item();
    }
}
