package com.bms.bms_server.dto.Employee.response;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EmployeeDTO {
    private Long id;
    private String username;
    private String fullName;
    private String phone;
    private LocalDate startDate;
    private LocalDate birthDate;
    private Integer gender;
    private String email;
    private String address;
    private Boolean status;
    private Integer role;
    private Boolean accessBms;
    private Boolean accessCms;
    private Boolean accessTms;
}
