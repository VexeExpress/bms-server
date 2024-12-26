package com.bms.bms_server.modules.ModuleVehicle.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Entity
@Table(name = "vehicle")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;

    @Column(name = "license_plate")
    String licensePlate; // Biển số xe

    @Column(name = "note")
    String note;

    @Column(name = "phone")
    String phone;

    @Column(name = "typeVehicle")
    Integer typeVehicle; // 1: Giường nằm, 2: Ghế ngồi, 3: Ghế ngồi limousine, 4: Giuờng nằm limousine, 5: Phòng VIP (Cabin)

    @Column(name = "registration_period")
    LocalDate registrationPeriod; // Hạn đăng kiểm

    @Column(name = "status")
    Integer status;

    @Column(name = "color")
    String color;

    @Column(name = "maintenance_period")
    LocalDate maintenancePeriod; // Hạn bảo dưỡng

    @Column(name = "brand")
    Integer brand; // Hãng xe
}
