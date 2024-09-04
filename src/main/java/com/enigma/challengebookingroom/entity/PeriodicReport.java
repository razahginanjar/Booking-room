package com.enigma.challengebookingroom.entity;

import java.time.LocalDateTime;

import com.enigma.challengebookingroom.constant.ConstantTable;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.REPORT)
@Builder
public class PeriodicReport {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "report_id", nullable = false, updatable = false, unique = true)
    private String reportId;

    @Column(name = "report_type", nullable = false)
    private String reportType;

    @Column(name = "report_gen_timestamp", nullable = false, updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportGenTimestamp;

    @Column(name = "report_content")
    private String reportContent;

    @PrePersist
    protected void onCreate() {
        this.reportGenTimestamp = LocalDateTime.now();
    }

}