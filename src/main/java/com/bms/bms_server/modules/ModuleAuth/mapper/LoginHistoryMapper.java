package com.bms.bms_server.modules.ModuleAuth.mapper;

import com.bms.bms_server.modules.ModuleAuth.dto.DTO_RP_LoginHistory;
import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;

public class LoginHistoryMapper {
    public static DTO_RP_LoginHistory toDTO(LoginHistory loginHistory) {
        DTO_RP_LoginHistory dto = new DTO_RP_LoginHistory();
        dto.setIpAddress(loginHistory.getIpAddress());
        dto.setBrowserName(loginHistory.getBrowserName());
        dto.setOperatingSystem(loginHistory.getOperatingSystem());
        dto.setTimeLogin(loginHistory.getTimeLogin());
        dto.setDateLogin(loginHistory.getDateLogin());
        dto.setUsername(loginHistory.getEmployee().getUsername());
        dto.setFullName(loginHistory.getEmployee().getFullName());
        return dto;
    }
}
