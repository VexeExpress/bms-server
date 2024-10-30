package com.bms.bms_server.entity;

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

    @Column(name = "phone")
    private String phone; // So dien thoai xe

    @Column(name = "color")
    private String color; // Màu xe

    @Column(name = "brand")
    private String brand; // Hãng xe

    @Column(name = "chassis_number")
    private String chassisNumber; // Số khung

    @Column(name = "engine_number")
    private String engineNumber; // Số máy

    @Column(name = "type")
    private Integer type; // 1: Giường nằm, 2: Ghế ngồi, 3: Ghế ngồi limousine, 4: Giuờng nằm limousine, 5: Phòng VIP (Cabin)

    @Column(name = "number_of_seat")
    private Integer numberOfSeat; // Số lượng ghế

    @Column(name = "registration_period")
    private LocalDate registrationPeriod; // Hạn đăng kiểm

    @Column(name = "insurance_period")
    private LocalDate insurancePeriod; // Hạn bảo hiểm

}
