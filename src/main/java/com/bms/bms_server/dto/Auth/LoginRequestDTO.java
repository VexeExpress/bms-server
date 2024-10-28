package com.bms.bms_server.dto.Auth;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
