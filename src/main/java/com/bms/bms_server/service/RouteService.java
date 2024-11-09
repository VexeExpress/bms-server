package com.bms.bms_server.service;

import com.bms.bms_server.dto.Route.RouteRequestDTO;
import com.bms.bms_server.dto.Route.RouteResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Route;
import com.bms.bms_server.entity.Vehicle;
import com.bms.bms_server.mapper.RouteMapper;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.RouteMatcher;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    CompanyRepository companyRepository;
    public RouteResponseDTO createRoute(RouteRequestDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required.");
        }
        if (dto.getRouteName() == null || dto.getRouteName().trim().isEmpty()) {
            throw new IllegalArgumentException("Route name is required.");
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found."));
        boolean exists = routeRepository.existsByCompanyAndRouteName(company, dto.getRouteName());
        if (exists) {
            throw new IllegalArgumentException("RouteName already exists for this company.");
        }
        Route route = RouteMapper.toEntity(dto, company);
        Route saved = routeRepository.save(route);
        return RouteMapper.toDTO(saved);
    }

    public List<RouteResponseDTO> getListRouteByCompanyId(Long companyId) {
        List<Route> routes = routeRepository.findByCompanyId(companyId);
        return routes.stream().map(RouteMapper::toDTO).collect(Collectors.toList());
    }

    public RouteResponseDTO updateRoute(Long routeId, RouteRequestDTO updatedData) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new EntityNotFoundException("Route with ID " + routeId + " not found"));
        if (updatedData.getRouteName() != null) {
            route.setRouteName(updatedData.getRouteName());
            route.setRouteNameShort(updatedData.getRouteNameShort());
            route.setDisplayPrice(updatedData.getDisplayPrice());
            route.setStatus(updatedData.getStatus());
            route.setNote(updatedData.getNote());
        }
        Route updateRoute = routeRepository.save(route);
        return RouteMapper.toDTO(updateRoute);
    }

    public void deleteRouteById(Long routeId) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new EntityNotFoundException("Tuyen không tồn tại"));
        routeRepository.delete(route);
    }
}
