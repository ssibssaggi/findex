package com.ssibssaggi.findex.controller;

import com.ssibssaggi.findex.application.index.IndexDataApplication;
import com.ssibssaggi.findex.controller.dto.IndexDataCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexDataResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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

    private final IndexDataApplication indexDataApplication;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public IndexDataResponse createIndexData(
        @RequestBody IndexDataCreateRequest createRequest) {
        //IndexDataResponse response = indexDataApplication.saveData(createRequest);
        //log.info("지수 데이터 등록: " + response);
        //return response;
        return indexDataApplication.saveData(createRequest);
    }
/*
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteIndexData(@PathVariable Long id) {
        IndexData indexData = indexDataService.read(id);
        IndexDataResponse response = IndexDataResponse.from(indexData);
        indexDataService.delete(id);
        log.info("삭제한 지수 데이터 id : " + id);
    }*/
}