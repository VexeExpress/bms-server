package com.bms.bms_server.dto.Auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private String fullName;
    private String companyName;
    private Long companyId;
    private String token;
}
