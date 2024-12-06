package com.bms.bms_server.modules.ModuleVehicle.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DTO_RP_Vehicle {
    private Long id;
    private String licensePlate;
    private String note;
    private String phone;
    private Integer typeVehicle;
    private LocalDate registrationPeriod;
    private Integer status;
    private String color;
    private LocalDate maintenancePeriod;
    private Integer brand;
}
