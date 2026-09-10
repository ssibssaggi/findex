package com.ssibssaggi.findex.domain.service;

import org.springframework.stereotype.Service;

import com.ssibssaggi.findex.repository.IndexInformationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IndexInformationService {
    private final IndexInformationRepository indexInformationRepository;
}
