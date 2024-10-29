package com.bms.bms_server.dto.Employee.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EmployeeRequestDTO {
    private String username;
    private String password;
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
    private Long companyId;
}
