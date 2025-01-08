package com.bms.bms_server.modules.ModuleAgent.dto;

import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_RouteName;
import lombok.Data;

import java.util.List;

@Data
public class DTO_RP_AssignAgentToRoute {
    private Long id;
    private List<Long> routes;
}
