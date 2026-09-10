package com.ssibssaggi.findex.service;

import com.ssibssaggi.findex.controller.dto.DataCreateRequest;
import com.ssibssaggi.findex.controller.dto.DataUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexData;

public interface IndexDataInterfaceService {
    IndexData userCreate(DataCreateRequest informationCreateRequest);
    IndexData read(Long id);
    IndexData update(Long id, DataUpdateRequest informationUpdateRequest);
    void delete(Long id);
}
