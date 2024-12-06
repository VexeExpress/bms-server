package com.bms.bms_server.modules.ModuleAuth.dto;

import lombok.Data;

@Data
public class DTO_RP_Login {
    private String fullName;
    private String companyName;
    private Long companyId;
    private String token;
}
