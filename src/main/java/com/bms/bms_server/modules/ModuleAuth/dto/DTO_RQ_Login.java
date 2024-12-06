package com.bms.bms_server.modules.ModuleAuth.dto;

import lombok.Data;

@Data
public class DTO_RQ_Login {
    private String username;
    private String password;
    private String ipAddress;
    private String browserName;
    private String operatingSystem;
}
