package com.bms.bms_server.dto.Vehicle.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class VehicleRequestDTO {
    private String licensePlate;
    private String phone;
    private String color;
    private String brand;
    private String chassisNumber;
    private String engineNumber;
    private Integer type;
    private Integer numberOfSeat;
    private LocalDate registrationPeriod;
    private LocalDate insurancePeriod;
    private Long companyId;
}
