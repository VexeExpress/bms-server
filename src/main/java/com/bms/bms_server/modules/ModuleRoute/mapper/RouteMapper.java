package com.bms.bms_server.modules.ModuleRoute.mapper;

import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_RouteName;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RQ_Route;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_Route;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;

public class RouteMapper {
    public static Route toEntity(DTO_RQ_Route dto, Company company) {
        Route route = new Route();
        route.setCompany(company);
        route.setRouteName(dto.getRouteName());
        route.setRouteNameShort(dto.getRouteNameShort());
        route.setDisplayPrice(dto.getDisplayPrice());
        route.setStatus(dto.getStatus());
        route.setNote(dto.getNote());
        return route;
    }
    public static DTO_RP_Route toDTO(Route route) {
        DTO_RP_Route dto = new DTO_RP_Route();
        dto.setId(route.getId());
        dto.setRouteName(route.getRouteName());
        dto.setRouteNameShort(route.getRouteNameShort());
        dto.setDisplayPrice(route.getDisplayPrice());
        dto.setStatus(route.getStatus());
        dto.setNote(route.getNote());
        dto.setDisplayOrder(route.getDisplayOrder());
        return dto;
    }
    public static DTO_RP_RouteName toRouteNameDTO(Route route) {
        DTO_RP_RouteName dto = new DTO_RP_RouteName();
        dto.setId(route.getId());
        dto.setRouteName(route.getRouteName());
        return dto;
    }
}
