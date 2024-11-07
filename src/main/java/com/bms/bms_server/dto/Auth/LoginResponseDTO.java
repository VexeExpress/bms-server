package com.bms.bms_server.dto.Auth;

import lombok.Data;

@Data
public class LoginResponseDTO {
    private Long id;
    private String fullName;
    private Long companyId;
    private String companyName;
}
