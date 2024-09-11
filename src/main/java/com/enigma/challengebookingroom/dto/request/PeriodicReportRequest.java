package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.enigma.challengebookingroom.entity.PeriodicReport}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class PeriodicReportRequest implements Serializable {
    String reportId;
    String reportType;
    LocalDateTime reportGenTimestamp;
    String reportContent;
}