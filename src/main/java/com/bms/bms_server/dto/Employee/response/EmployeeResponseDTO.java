package com.bms.bms_server.dto.Employee.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EmployeeResponseDTO {
    private Long id;
    private String username;
    private Boolean status;
    private String fullName;
    private String phoneNumber;
    private String address;
    private String email;
    private String idCard;
    private Boolean gender;
    private LocalDate birthDate;
    private Integer role;
    private Integer licenseCategory;
    private LocalDate expirationDate;
}
