package com.ssibssaggi.findex.domain.entity.integrationhistory;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import com.ssibssaggi.findex.domain.entity.index.IndexInformation;

import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class IntegrationHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private JobType jobtype;
    private LocalDate targetDate;
    private String worker;
    private LocalDateTime jobTime;
    private IntegrationResult result;

    @ManyToOne
    @JoinColumn(name = "index_information_id")
    private IndexInformation indexInformation;
}