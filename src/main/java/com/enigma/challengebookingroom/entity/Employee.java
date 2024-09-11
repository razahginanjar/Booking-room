package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = ConstantTable.EMPLOYEE)
@Builder
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private String employeeId;

    @Column(name = "employee_name", nullable = false, unique = true)
    private String employeeName;

    @Column(name = "department", nullable = false)
    private String department;

    @Column(name = "phone_number", unique = true)
    private String phoneNumber;

    @Column(name = "corporate_email", nullable = false, unique = true)
    private String corporateEmail;

}