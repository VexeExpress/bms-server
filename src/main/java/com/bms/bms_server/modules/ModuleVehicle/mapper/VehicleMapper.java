package com.bms.bms_server.modules.ModuleVehicle.mapper;

import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_LicensePlate;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RQ_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_Vehicle;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleVehicle.entity.Vehicle;

public class VehicleMapper {
    public static Vehicle toEntity(DTO_RQ_Vehicle dto, Company company) {
        Vehicle vehicle = new Vehicle();
        vehicle.setCompany(company);
        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setNote(dto.getNote());
        vehicle.setPhone(dto.getPhone());
        vehicle.setTypeVehicle(dto.getTypeVehicle());
        vehicle.setRegistrationPeriod(dto.getRegistrationPeriod());
        vehicle.setStatus(dto.getStatus());
        vehicle.setColor(dto.getColor());
        vehicle.setMaintenancePeriod(dto.getMaintenancePeriod());
        vehicle.setBrand(dto.getBrand());
        return vehicle;
    }
    public static DTO_RP_Vehicle toResponseDTO(Vehicle vehicle) {
        DTO_RP_Vehicle dto = new DTO_RP_Vehicle();
        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        dto.setNote(vehicle.getNote());
        dto.setPhone(vehicle.getPhone());
        dto.setTypeVehicle(vehicle.getTypeVehicle());
        dto.setRegistrationPeriod(vehicle.getRegistrationPeriod());
        dto.setStatus(vehicle.getStatus());
        dto.setColor(vehicle.getColor());
        dto.setMaintenancePeriod(vehicle.getMaintenancePeriod());
        dto.setBrand(vehicle.getBrand());
        return dto;
    }
    public static DTO_RP_LicensePlate toResponseLicenseDTO(Vehicle vehicle) {
        DTO_RP_LicensePlate dto = new DTO_RP_LicensePlate();
        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        return dto;
    }
}
