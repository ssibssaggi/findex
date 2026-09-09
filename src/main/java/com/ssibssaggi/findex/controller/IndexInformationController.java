package com.ssibssaggi.findex.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssibssaggi.findex.application.IndexInformationApplication;
import com.ssibssaggi.findex.controller.dto.IndexInformationRequest;
import com.ssibssaggi.findex.controller.dto.IndexInformationResponse;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndexInformationController {
    private final IndexInformationApplication indexInformationApplication;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = "/api/index-infos")
    public IndexInformationResponse createIndexInfo(
            @RequestBody IndexInformationRequest indexInformationRequest
    ) {
        return indexInformationApplication.saveInformation(indexInformationRequest);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/api/index-infos/{id}")
    public IndexInformationResponse getIndexInfoById(
            @PathVariable Long id
    ) {
        return indexInformationApplication.findById(id);
    }
}
