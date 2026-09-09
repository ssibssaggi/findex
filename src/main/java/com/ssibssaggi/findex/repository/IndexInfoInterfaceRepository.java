package com.ssibssaggi.findex.repository;

import com.ssibssaggi.findex.controller.dto.InformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.InformationFindRequest;
import com.ssibssaggi.findex.controller.dto.InformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public interface IndexInfoInterfaceRepository {
    IndexInformation create(InformationCreateRequest informationCreateRequest);

    IndexInformation read(InformationFindRequest informationFindRequest);

    IndexInformation update(Long id, InformationUpdateRequest informationUpdateRequest);

    void delete(Long id);
}
