package com.ssibssaggi.findex.application.index;

import com.ssibssaggi.findex.service.IndexDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndexDataApplication {

    private final IndexDataService indexDataService;
}
