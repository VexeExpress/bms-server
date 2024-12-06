package com.bms.bms_server.modules.ModuleAuth.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class DTO_RP_LoginHistory {
    private String ipAddress;
    private String browserName;
    private String operatingSystem;
    private LocalTime timeLogin;
    private LocalDate dateLogin;
    private String username;
    private String fullName;
}
