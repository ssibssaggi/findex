package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class IndexInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String indexClassification;
    private String indexName;
    private Integer employedItemsCount;
    private LocalDate basePointInTime;
    private Integer baseIndex;
    private SourceType sourceType;
    private Boolean favorite;
    private Boolean enabled;

    public void update(
            Integer employedItemsCount, String basePointInTime,
            Integer baseIndex, Boolean favorite
    ) {
        this.employedItemsCount = employedItemsCount;
        this.basePointInTime = LocalDate.parse(basePointInTime);
        this.baseIndex = baseIndex;
        this.favorite = favorite;
    }

    public static IndexInformation create(IndexInformationCreateRequest request, SourceType sourceType) {
        IndexInformation entity = new IndexInformation();
        entity.indexName = request.indexName();
        entity.indexClassification = request.indexClassification();
        entity.employedItemsCount = request.employedItemsCount();
        entity.basePointInTime = LocalDate.parse(request.basePointInTime());
        entity.baseIndex = request.baseIndex().intValue();
        entity.favorite = request.favorite();
        entity.sourceType = sourceType;
        entity.enabled = true;
        return entity;
    }
}