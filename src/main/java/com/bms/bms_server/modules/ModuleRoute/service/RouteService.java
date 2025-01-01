package com.bms.bms_server.modules.ModuleRoute.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_RouteName;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RQ_Route;
import com.bms.bms_server.modules.ModuleRoute.dto.DTO_RP_Route;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleRoute.mapper.RouteMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleRoute.repository.RouteRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    CompanyRepository companyRepository;

    // PB.04_US.01: Add new route
    public DTO_RP_Route createRoute(DTO_RQ_Route dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getRouteName() == null || dto.getRouteName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_ROUTE_NAME);
        }
        if (dto.getDisplayPrice() == null) {
            throw new AppException(ErrorCode.INVALID_DISPLAY_PRICE);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        boolean exists = routeRepository.existsByCompanyAndRouteName(company, dto.getRouteName());
        if (exists) {
            throw new AppException(ErrorCode.ROUTE_ALREADY_EXISTED);
        }
        Integer maxDisplayOrder = routeRepository.findMaxDisplayOrderByCompany(company).orElse(0);
        Route route = RouteMapper.toEntity(dto, company);
        route.setDisplayOrder(maxDisplayOrder + 1);
        Route saved = routeRepository.save(route);
        return RouteMapper.toDTO(saved);
    }

    // PB.04_US.02: Filter/Get list route
    public List<DTO_RP_Route> getListRouteByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Route> routes = routeRepository.findByCompanyIdOrderByDisplayOrderAsc(companyId);
        return routes.stream().map(RouteMapper::toDTO).collect(Collectors.toList());
    }

    // PB.04_US.03: Filter/Get list route name
    public List<DTO_RP_RouteName> getListRouteNameByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Route> routes = routeRepository.findByCompanyIdOrderByDisplayOrderAsc(companyId);
        return routes.stream()
                .filter(Route::getStatus) // Only include routes where status is true
                .map(RouteMapper::toRouteNameDTO)
                .collect(Collectors.toList());
    }

    // PB.04_US.04: Update route information
    public DTO_RP_Route updateRoute(Long routeId, DTO_RQ_Route dto) {
        Route route = routeRepository.findById(routeId).orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));
        if (dto.getRouteName() == null || dto.getRouteName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_ROUTE_NAME);
        }
        if (dto.getDisplayPrice() == null) {
            throw new AppException(ErrorCode.INVALID_DISPLAY_PRICE);
        }
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        route.setRouteName(dto.getRouteName());
        route.setRouteNameShort(dto.getRouteNameShort());
        route.setDisplayPrice(dto.getDisplayPrice());
        route.setStatus(dto.getStatus());
        route.setNote(dto.getNote());

        Route updateRoute = routeRepository.save(route);
        return RouteMapper.toDTO(updateRoute);
    }

    // PB.04_US.05: Remove route
    public void deleteRouteById(Long routeId) {
        Optional<Route> routeOptional = routeRepository.findById(routeId);
        if (routeOptional.isEmpty()) {
            throw new AppException(ErrorCode.ROUTE_NOT_FOUND);
        }
        routeRepository.deleteById(routeId);
    }

    // PB.04_US.06: Move route display order
    public void moveRouteToTop(Long routeId) {
        Route route = routeRepository.findById(routeId)
                .orElseThrow(() -> new AppException(ErrorCode.ROUTE_NOT_FOUND));

        Long companyId = route.getCompany().getId();
        int currentOrder = route.getDisplayOrder();

        // Nếu đã ở vị trí cao nhất, không thể di chuyển lên nữa
        if (currentOrder == 1) {
            throw new AppException(ErrorCode.ROUTE_ALREADY_AT_TOP);
        }

        // Tìm tuyến có `displayOrder` liền trước trong cùng công ty
        Route previousRoute = routeRepository
                .findByCompanyIdAndDisplayOrder(companyId, currentOrder - 1)
                .orElseThrow(() -> new AppException(ErrorCode.INCONSISTENT_DISPLAY_ORDER));

        // Hoán đổi displayOrder của hai tuyến
        previousRoute.setDisplayOrder(currentOrder);
        route.setDisplayOrder(currentOrder - 1);

        routeRepository.save(previousRoute);
        routeRepository.save(route);
    }



}
