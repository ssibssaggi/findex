package com.ssibssaggi.findex.repository;

import java.util.List;

import com.ssibssaggi.findex.controller.dto.CursorPageCondition;
import com.ssibssaggi.findex.controller.dto.IndexInfoSearchCondition;
import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public interface IndexInformationRepositoryCustom {
    List<IndexInformation> searchIndexInfos(
            IndexInfoSearchCondition indexInfoSearchCondition,
            CursorPageCondition cursorPageCondition
    );

    Long count(IndexInfoSearchCondition searchCondition);
}
