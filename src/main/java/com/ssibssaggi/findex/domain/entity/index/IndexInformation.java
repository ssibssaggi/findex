package com.ssibssaggi.findex.domain.entity.index;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;

//지수 자체의 정보
@Getter
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
    @Column(name = "favorite")
    private Boolean isFavorite;
    @Column(name = "enabled")
    private Boolean isEnabled;
}
