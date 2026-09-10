package com.ssibssaggi.findex.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssibssaggi.findex.domain.entity.integrationhistory.IntegrationHistory;

public interface IntegrationHistoryRepository extends JpaRepository<IntegrationHistory, Long> {
}
