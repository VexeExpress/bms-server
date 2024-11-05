package com.bms.bms_server.dto.Employee.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class EditEmployeeDTO {
    private String username;
    private String fullName;
    private String phone;
    private LocalDate startDate;
    private LocalDate birthDate;
    private Integer gender;
    private String email;
    private String address;
    private Integer status;
    private Integer role;
}
