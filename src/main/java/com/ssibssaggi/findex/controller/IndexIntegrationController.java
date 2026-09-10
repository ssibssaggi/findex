package com.ssibssaggi.findex.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.ssibssaggi.findex.application.indexintegration.IndexIntegrationApplication;
import com.ssibssaggi.findex.controller.dto.SyncJobDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class IndexIntegrationController {
    private final IndexIntegrationApplication indexIntegrationApplication;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("/api/sync-jobs/index-infos")
    public List<SyncJobDto> syncIndexInfo(HttpServletRequest request) {
        return indexIntegrationApplication.syncIndexInfoWithOpenApi(request.getRemoteAddr());
    }
}
