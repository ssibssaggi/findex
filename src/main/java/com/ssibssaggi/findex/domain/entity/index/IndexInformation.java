package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.NoArgsConstructor;

@Entity
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
}