package com.ssibssaggi.findex.client.openapi;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.ssibssaggi.findex.common.exception.CustomException;

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
                                // API 서버가 영업일 하루 뒤 오후 1시에 업데이트 되므로, 일단 임시 방편으로 1을 빼놓음
                                // 주말, 공휴일 등 고려해서 가능한 가장 최신으로 업데이트하는 로직 필요할 듯
                                .queryParam("likeBasDt", localDateToYyyyMmDd(
                                        LocalDate.now(ZoneId.systemDefault()).minusDays(1)))
                                .build(serviceKey)
                        )
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(IndexInfoApiResponse.class))
                .orElseThrow(() -> new CustomException(
                        "OpenAPI 호출 오류",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "OpenAPI 가 정상적으로 호출되지 않습니다."
                ));

        return response.toIndexInfoApiItem();
    }

    private String localDateToYyyyMmDd(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public List<IndexDataApiItem> syncIndexData(IndexDataSyncWithApiCommand command) {
        return Optional.ofNullable(indexRestClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .queryParam("serviceKey", "{serviceKey}")
                                .queryParam("resultType", "json")
                                .queryParam("pageNo", 1)
                                .queryParam("numOfRows", 10000)
                                .queryParam("idxNm", "{indexName}")
                                .queryParam("beginBasDt", "{baseDateFrom}")
                                .queryParam("endBasDt", "{baseDateTo}")
                                .build(serviceKey,
                                        command.indexName(),
                                        localDateToYyyyMmDd(command.baseDateFrom()),
                                        // API에 endBasDt 이전 날짜만 응답에 포함하므로 1일을 더함.
                                        localDateToYyyyMmDd(command.baseDateTo().plusDays(1))
                                )
                        )
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .body(IndexDataApiResponse.class)
                )
                .orElseThrow(() -> new CustomException(
                        "OpenAPI 호출 오류",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        "OpenAPI 가 정상적으로 호출되지 않습니다."
                ))
                .toIndexDataApiItems();
    }
}
