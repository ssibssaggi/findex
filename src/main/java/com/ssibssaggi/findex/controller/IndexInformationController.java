package com.ssibssaggi.findex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssibssaggi.findex.application.IndexInformationApplication;
import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationResponse;
import com.ssibssaggi.findex.controller.dto.IndexInformationUpdateRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndexInformationController {
    private final IndexInformationApplication indexInformationApplication;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/index-infos")
    public IndexInformationResponse createIndexInfo(
            @RequestBody IndexInformationCreateRequest indexInformationCreateRequest
    ) {
        return indexInformationApplication.saveInformation(indexInformationCreateRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/index-infos/{id}")
    public IndexInformationResponse getIndexInfoById(
            @PathVariable Long id
    ) {
        return indexInformationApplication.findById(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(value = "/api/index-infos/{id}")
    public void deleteIndexInfoById(
            @PathVariable Long id
    ) {
        indexInformationApplication.deleteById(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @PatchMapping(value = "/api/index-infos/{id}")
    public IndexInformationResponse updateIndexInfoById(
            @PathVariable Long id,
            @RequestBody IndexInformationUpdateRequest indexInformationUpdateRequest
    ) {
        return indexInformationApplication.update(id, indexInformationUpdateRequest);
    }
}
