package com.bms.bms_server.modules.ModuleEmployee.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

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
    private Set<String> roles;
    private Boolean accessBms;
    private Boolean accessCms;
    private Boolean accessTms;
}
