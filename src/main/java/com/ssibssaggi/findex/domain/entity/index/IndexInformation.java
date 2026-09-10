package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import com.ssibssaggi.findex.controller.dto.IndexInformationCreateRequest;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"index_classification", "index_name"})
})
public class IndexInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String indexClassification;
    private String indexName;
    private Integer employedItemsCount;
    private LocalDate basePointInTime;
    private Float baseIndex;
    private SourceType sourceType;
    private Boolean favorite;
    private Boolean enabled;

    public static IndexInformation createWithUser(IndexInformationCreateRequest request) {
        IndexInformation entity = new IndexInformation();
        entity.indexName = request.indexName();
        entity.indexClassification = request.indexClassification();
        entity.employedItemsCount = request.employedItemsCount();
        entity.basePointInTime = LocalDate.parse(request.basePointInTime());
        entity.baseIndex = request.baseIndex();
        entity.favorite = request.favorite();
        entity.sourceType = SourceType.USER;
        entity.enabled = false;
        return entity;
    }

    public static IndexInformation createWithOpenApi(
            String indexClassification,
            String indexName,
            Integer employedItemsCount,
            LocalDate basePointInTime,
            Float baseIndex
    ) {
        return new IndexInformation(
                null,
                indexClassification,
                indexName,
                employedItemsCount,
                basePointInTime,
                baseIndex,
                SourceType.OPEN_API,
                false,
                false
        );
    }

    public void updateWithUser(
            Integer employedItemsCount,
            String basePointInTime,
            Float baseIndex,
            Boolean favorite
    ) {
        this.employedItemsCount = employedItemsCount;
        this.basePointInTime = LocalDate.parse(basePointInTime);
        this.baseIndex = baseIndex;
        this.favorite = favorite;
    }

    public void updateWithOpenApi(
            Integer employedItemsCount,
            LocalDate basePointInTime,
            Float baseIndex
    ) {
        this.employedItemsCount = employedItemsCount;
        this.basePointInTime = basePointInTime;
        this.baseIndex = baseIndex;
    }
}
