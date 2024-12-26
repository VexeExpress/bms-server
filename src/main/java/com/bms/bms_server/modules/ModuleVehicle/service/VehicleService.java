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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;


    public DTO_RP_Vehicle createVehicle(DTO_RQ_Vehicle dto) {
        if (dto.getLicensePlate() == null || dto.getLicensePlate().trim().isEmpty()) {
            throw new AppException(ErrorCode.VEHICLE_LICENSE_PLATE_REQUIRED);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        boolean exists = vehicleRepository.existsByCompanyAndLicensePlate(company, dto.getLicensePlate());
        if (exists) {
            throw new AppException(ErrorCode.VEHICLE_EXISTED);
        }
        Vehicle vehicle = VehicleMapper.toEntity(dto, company);
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDTO(saved);
    }

    public List<DTO_RP_Vehicle> getListVehicleByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.COMPANY_NOT_EXIST);
        }
        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);
        if (vehicles.isEmpty()) {
            throw new AppException(ErrorCode.VEHICLES_NOT_FOUND);
        }
        return vehicles.stream().map(VehicleMapper::toResponseDTO).collect(Collectors.toList());
    }

    public DTO_RP_Vehicle updateVehicle(Long vehicleId, DTO_RQ_Vehicle updatedData) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        if (updatedData.getLicensePlate() != null) {
            vehicle.setLicensePlate(updatedData.getLicensePlate());
            vehicle.setNote(updatedData.getNote());
            vehicle.setPhone(updatedData.getPhone());
            vehicle.setTypeVehicle(updatedData.getTypeVehicle());
            vehicle.setRegistrationPeriod(updatedData.getRegistrationPeriod());
            vehicle.setStatus(updatedData.getStatus());
            vehicle.setColor(updatedData.getColor());
            vehicle.setMaintenancePeriod(updatedData.getMaintenancePeriod());
            vehicle.setBrand(updatedData.getBrand());
        }
        Vehicle updateVehicle = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDTO(updateVehicle);
    }

    public void deleteVehicleById(Long vehicleId) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new AppException(ErrorCode.VEHICLE_NOT_FOUND));
        vehicleRepository.delete(vehicle);
    }

    public List<DTO_RP_LicensePlate> getLicensePlateVehicleByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.COMPANY_NOT_EXIST);
        }
        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);
        if (vehicles.isEmpty()) {
            throw new AppException(ErrorCode.VEHICLES_NOT_FOUND);
        }
        return vehicles.stream()
                .filter(vehicle -> vehicle.getStatus() == 1)
                .map(VehicleMapper::toResponseLicenseDTO)
                .collect(Collectors.toList());
    }
}
