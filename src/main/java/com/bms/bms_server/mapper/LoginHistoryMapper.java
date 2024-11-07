package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Auth.LoginHistoryResponseDTO;
import com.bms.bms_server.entity.LoginHistory;

public class LoginHistoryMapper {
    public static LoginHistoryResponseDTO toDTO(LoginHistory loginHistory) {
        LoginHistoryResponseDTO dto = new LoginHistoryResponseDTO();
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
