package com.ssibssaggi.findex.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

public interface IndexInformationJpaRepository extends JpaRepository<IndexInformation, Long> {
    List<IndexInformation> findByFavoriteTrueAndEnabledTrue();

    List<IndexInformation> findByEnabledTrue();
}
