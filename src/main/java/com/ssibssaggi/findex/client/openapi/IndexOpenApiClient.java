package com.ssibssaggi.findex.client.openapi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class IndexOpenApiClient {
    private final RestClient indexRestClient;

    @Value("${index-api.service-key}")
    private String serviceKey;

    public List<IndexInfoApiItem> syncIndexInformation() {
        IndexInfoApiResponse response = Optional.ofNullable(indexRestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("serviceKey", "{serviceKey}")
                                .queryParam("resultType", "json")
                                .queryParam("pageNo", 1)
                                .queryParam("numOfRows", 10000)
                                .queryParam("likeBasDt", localDateNowToYyyyMmDd())
                                .build(serviceKey)
                        )
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(IndexInfoApiResponse.class))
                .orElseThrow();

        return response.toIndexInfoApiItem();
    }

    private String localDateNowToYyyyMmDd() {
        return LocalDate.now(ZoneId.systemDefault())
                .minusDays(2)   // 전날 정보가 13시에 갱신됨. 13시 이전이면 2로 두고 테스트할 것.
                .format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
}
