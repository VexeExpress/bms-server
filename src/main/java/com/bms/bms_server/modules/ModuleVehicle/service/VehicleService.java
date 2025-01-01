package com.bms.bms_server.modules.ModuleVehicle.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_LicensePlate;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RQ_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_Vehicle;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleVehicle.entity.Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.mapper.VehicleMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleVehicle.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;

    // PB.03_US.01: Add new vehicle
    public DTO_RP_Vehicle createVehicle(DTO_RQ_Vehicle dto) {
        if (dto.getLicensePlate() == null || dto.getLicensePlate().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_LICENSE_PLATE);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        boolean exists = vehicleRepository.existsByCompanyAndLicensePlate(company, dto.getLicensePlate());
        if (exists) {
            throw new AppException(ErrorCode.VEHICLE_ALREADY_EXISTED);
        }

        Vehicle vehicle = VehicleMapper.toEntity(dto, company);
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDTO(saved);
    }

    // PB.03_US.02: Filter/Get Vehicle List
    public List<DTO_RP_Vehicle> getListVehicleByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);
        return vehicles.stream().map(VehicleMapper::toResponseDTO).collect(Collectors.toList());
    }

    // PB.03_US.03: Update vehicle information
    public DTO_RP_Vehicle updateVehicle(Long vehicleId, DTO_RQ_Vehicle dto) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        if (dto.getLicensePlate() == null || dto.getLicensePlate().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_LICENSE_PLATE);
        }
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        vehicle.setLicensePlate(dto.getLicensePlate());
        vehicle.setNote(dto.getNote());
        vehicle.setPhone(dto.getPhone());
        vehicle.setTypeVehicle(dto.getTypeVehicle());
        vehicle.setRegistrationPeriod(dto.getRegistrationPeriod());
        vehicle.setStatus(dto.getStatus());
        vehicle.setColor(dto.getColor());
        vehicle.setMaintenancePeriod(dto.getMaintenancePeriod());
        vehicle.setBrand(dto.getBrand());

        Vehicle updateVehicle = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDTO(updateVehicle);
    }

    // PB.03_US.04: Remove vehicle
    public void deleteVehicleById(Long vehicleId) {
        Optional<Vehicle> vehicleOptional = vehicleRepository.findById(vehicleId);
        if (vehicleOptional.isEmpty()) {
            throw new AppException(ErrorCode.VEHICLE_NOT_FOUND);
        }
        vehicleRepository.deleteById(vehicleId);
    }

    // PB.03_US.05: Filter/Get list of license plates
    public List<DTO_RP_LicensePlate> getLicensePlateVehicleByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);
        return vehicles.stream()
                .filter(vehicle -> vehicle.getStatus() == 1)
                .map(VehicleMapper::toResponseLicenseDTO)
                .collect(Collectors.toList());
    }
}
