package com.bms.bms_server.modules.ModuleEmployee.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "employee")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    @Column(name = "accept_bms")
    Boolean accessBms; // Bus Management System

    @Column(name = "accept_cms")
    Boolean accessCms; // Cargo Management System

    @Column(name = "accept_tms")
    Boolean accessTms; // Ticket Management System

    @Column(name = "username", length = 50)
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "role")
    Set<String> roles;

    @Column(name = "full_name")
    String fullName;

    @Column(name = "phone")
    String phone;

    @Column(name = "startDate")
    LocalDate startDate; // Ngày bắt đầu làm việc

    @Column(name = "birthDate")
    LocalDate birthDate; // Ngày sinh

    @Column(name = "gender")
    Integer gender; // 1: Nam, // 2: Nữ, // 3: Khác

    @Column(name = "email")
    String email;

    @Column(name = "address")
    String address;

    @Column(name = "status")
    Boolean status;



    @Column(name = "created_at")
    @CreationTimestamp
    LocalDate createdAt;
}
