package com.bms.bms_server.modules.ModuleAgent.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTO_RQ_AssignAgentToRoute {
    private List<Long> routeIds;
}
