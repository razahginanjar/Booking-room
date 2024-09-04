package com.enigma.challengebookingroom.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link com.enigma.challengebookingroom.entity.Employee}
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmployeeRequest implements Serializable {
    String employeeId;
    String employeeName;
    String department;
    String phoneNumber;
    String corporateEmail;
}