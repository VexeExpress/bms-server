package com.bms.bms_server.modules.ModuleEmployee.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class DTO_RQ_EditEmployee {
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
