package com.ssibssaggi.findex.controller;

import com.ssibssaggi.findex.controller.dto.DataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataResponse;
import com.ssibssaggi.findex.domain.entity.index.IndexData;
import com.ssibssaggi.findex.service.IndexDataService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/index-data")
@RequiredArgsConstructor
public class IndexDataController {
    private final IndexDataService indexDataService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public IndexDataResponse createIndexData (@RequestBody @Valid DataCreateRequest createRequest) {
        IndexData indexData = indexDataService.userCreate(createRequest);
        IndexDataResponse response = IndexDataResponse.from(indexData);
        log.info("지수 데이터 등: " + response);
        return response;
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public IndexDataResponse deleteIndexData(@PathVariable Long id) {
        IndexData indexData = indexDataService.read(id);
        IndexDataResponse response = IndexDataResponse.from(indexData);
        indexDataService.delete(id);
        log.info("삭제한 지수 데이터 id : " + id);
        return response;
    }
}
