package com.enigma.challengebookingroom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enigma.challengebookingroom.entity.PeriodicReport;

@Repository
public interface PeriodicReportRepository extends JpaRepository<PeriodicReport, String> {
}