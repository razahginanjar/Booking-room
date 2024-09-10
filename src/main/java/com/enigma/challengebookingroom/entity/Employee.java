package com.enigma.challengebookingroom.entity;

import com.enigma.challengebookingroom.constant.ConstantTable;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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

//    @OneToOne(fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.ALL)
//    @JoinColumn(name = "user_id")
//    private User user;
}