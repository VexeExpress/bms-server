package com.bms.bms_server.modules.ModuleCompany.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "company")
@Data
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", length = 100)
    private String companyName; // Tên công ty

    @Column(name = "address", length = 255)
    private String address;

    @Column(name = "phone_number", length = 13)
    private String phoneNumber;

    @Column(name = "status")
    private Boolean status; // Trạng thái công ty

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
