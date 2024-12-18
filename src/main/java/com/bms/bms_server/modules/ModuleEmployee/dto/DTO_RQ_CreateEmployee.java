package com.bms.bms_server.modules.ModuleEmployee.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class DTO_RQ_CreateEmployee {
    private String username;
    private String password;
    private Boolean accessBms;
    private Boolean accessCms;
    private Boolean accessTms;
    private String fullName;
    private String phone;
    private LocalDate startDate;
    private LocalDate birthDate;
    private Integer gender;
    private String email;
    private String address;
    private Boolean status;
    private Integer role;
    private Long companyId;
}
