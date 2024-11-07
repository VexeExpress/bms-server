package com.bms.bms_server.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "login_history")
@Data

public class LoginHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false)
    private Employee employee;

    @Column(name = "ip_address")
    private String ipAddress;

    @Column(name = "browser_name")
    private String browserName;

    @Column(name = "operating_system")
    private String operatingSystem;

    @Column(name = "time_login")
    private LocalTime timeLogin;

    @Column(name = "date_login")
    private LocalDate dateLogin;
}
