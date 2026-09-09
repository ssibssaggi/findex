package com.ssibssaggi.findex.repository;

import com.ssibssaggi.findex.controller.dto.InformationCreateRequest;
import com.ssibssaggi.findex.controller.dto.InformationFindRequest;
import com.ssibssaggi.findex.controller.dto.InformationUpdateRequest;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public class IndexInformationRepository implements IndexInfoInterfaceRepository {
    @Override
    public IndexInformation create(InformationCreateRequest informationCreateRequest) {
        return null;
    }

    @Override
    public IndexInformation read(InformationFindRequest informationFindRequest) {
        return null;
    }

    @Override
    public IndexInformation update(Long id, InformationUpdateRequest informationUpdateRequest) {
        return null;
    }

    @Override
    public void delete(Long id) {
    }
}