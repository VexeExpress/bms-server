package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Route.RouteRequestDTO;
import com.bms.bms_server.dto.Route.RouteResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Route;

public class RouteMapper {
    public static Route toEntity(RouteRequestDTO dto, Company company) {
        Route route = new Route();
        route.setCompany(company);
        route.setRouteName(dto.getRouteName());
        route.setRouteNameShort(dto.getRouteNameShort());
        route.setDisplayPrice(dto.getDisplayPrice());
        route.setStatus(dto.getStatus());
        route.setNote(dto.getNote());
        return route;
    }
    public static RouteResponseDTO toDTO(Route route) {
        RouteResponseDTO dto = new RouteResponseDTO();
        dto.setId(route.getId());
        dto.setRouteName(route.getRouteName());
        dto.setRouteNameShort(route.getRouteNameShort());
        dto.setDisplayPrice(route.getDisplayPrice());
        dto.setStatus(route.getStatus());
        dto.setNote(route.getNote());
        dto.setDisplayOrder(route.getDisplayOrder());
        return dto;
    }
}
