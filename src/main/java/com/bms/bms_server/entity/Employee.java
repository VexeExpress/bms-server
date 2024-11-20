package com.bms.bms_server.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "employee")
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "accept_bms", nullable = false)
    private Boolean accessBms; // Bus Management System

    @Column(name = "accept_cms", nullable = false)
    private Boolean accessCms; // Cargo Management System

    @Column(name = "accept_tms", nullable = false)
    private Boolean accessTms; // Ticket Management System

    @Column(name = "username", length = 50, nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private Integer role; // 1: Phụ xe, // 2: Tài xế, // 3: Nhân viên, // 4: Quản lý

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "startDate")
    private LocalDate startDate; // Ngày bắt đầu làm việc

    @Column(name = "birthDate")
    private LocalDate birthDate; // Ngày sinh

    @Column(name = "gender")
    private Integer gender; // 1: Nam, // 2: Nữ, // 3: Khác

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "status")
    private Boolean status; // true: Hoạt động, // false: Khóa



    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
