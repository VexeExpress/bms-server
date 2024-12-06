package com.bms.bms_server.modules.ModuleVehicle.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "vehicle")
@Data
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "license_plate")
    private String licensePlate; // Biển số xe

    @Column(name = "note")
    private String note;

    @Column(name = "phone")
    private String phone;

    @Column(name = "typeVehicle")
    private Integer typeVehicle; // 1: Giường nằm, 2: Ghế ngồi, 3: Ghế ngồi limousine, 4: Giuờng nằm limousine, 5: Phòng VIP (Cabin)

    @Column(name = "registration_period")
    private LocalDate registrationPeriod; // Hạn đăng kiểm

    @Column(name = "status")
    private Integer status;

    @Column(name = "color")
    private String color;

    @Column(name = "maintenance_period")
    private LocalDate maintenancePeriod; // Hạn bảo dưỡng

    @Column(name = "brand")
    private Integer brand; // Hãng xe
}
