package com.bms.bms_server.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

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

    @Column(name = "username", length = 50)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private Boolean status; // Trạng thái tài khoản

    @Column(name = "full_name", length = 100)
    private String fullName;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "id_card", length = 30)
    private String idCard; // Căn cước công dân

    @Column(name = "gender")
    private Boolean gender; // true = Nam, false = Nữ

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "role")
    private Integer role; //1(Tài xế) // 2(Nhân viên hành chính) // 3(Quản Trị Viên)

    @Column(name = "license_category")
    private Integer licenseCategory; // Loại bằng lái

    @Column(name = "expiration_date")
    private LocalDate expirationDate; // Ngày hết hạn bằng lái

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
