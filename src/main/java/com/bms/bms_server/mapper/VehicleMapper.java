package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Vehicle.LicensePlateVehicleResponseDTO;
import com.bms.bms_server.dto.Vehicle.VehicleRequestDTO;
import com.bms.bms_server.dto.Vehicle.VehicleResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Vehicle;

public class VehicleMapper {
    public static Vehicle toEntity(VehicleRequestDTO dto, Company company) {
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
    public static VehicleResponseDTO toResponseDTO(Vehicle vehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
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
    public static LicensePlateVehicleResponseDTO toResponseLicenseDTO(Vehicle vehicle) {
        LicensePlateVehicleResponseDTO dto = new LicensePlateVehicleResponseDTO();
        dto.setId(vehicle.getId());
        dto.setLicensePlate(vehicle.getLicensePlate());
        return dto;
    }
}
