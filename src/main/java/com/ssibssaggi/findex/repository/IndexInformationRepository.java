package com.ssibssaggi.findex.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

@Repository
public interface IndexInformationRepository extends JpaRepository<IndexInformation, Long> {
    Optional<IndexInformation> findIndexInformationByIndexClassificationAndIndexName(
            String indexClassification,
            String indexName
    );
}
