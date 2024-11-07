package com.bms.bms_server.dto.Auth;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class LoginHistoryResponseDTO {
    private String ipAddress;
    private String browserName;
    private String operatingSystem;
    private LocalTime timeLogin;
    private LocalDate dateLogin;
    private String username;
    private String fullName;
}
