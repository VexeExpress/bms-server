package com.bms.bms_server.modules.ModuleAuth.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DTO_RQ_Login {
    String username;
    String password;
    String ipAddress;
    String browserName;
    String operatingSystem;
}
