package com.bms.bms_server.service;

import com.bms.bms_server.dto.Vehicle.VehicleRequestDTO;
import com.bms.bms_server.dto.Vehicle.VehicleResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Vehicle;
import com.bms.bms_server.mapper.VehicleMapper;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.VehicleRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;


    public VehicleResponseDTO createVehicle(VehicleRequestDTO dto) {
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required.");
        }
        if (dto.getLicensePlate() == null || dto.getLicensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException("Vehicle name is required.");
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found."));
        boolean exists = vehicleRepository.existsByCompanyAndLicensePlate(company, dto.getLicensePlate());
        if (exists) {
            throw new IllegalArgumentException("LicensePlate already exists for this company.");
        }
        Vehicle vehicle = VehicleMapper.toEntity(dto, company);
        Vehicle saved = vehicleRepository.save(vehicle);
        return VehicleMapper.toResponseDTO(saved);
    }

    public List<VehicleResponseDTO> getListVehicleByCompanyId(Long companyId) {
        List<Vehicle> vehicles = vehicleRepository.findByCompanyId(companyId);
        return vehicles.stream().map(VehicleMapper::toResponseDTO).collect(Collectors.toList());
    }

    public VehicleResponseDTO updateVehicle(Long vehicleId, VehicleRequestDTO updatedData) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Vehicle with ID " + vehicleId + " not found"));
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
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow(() -> new EntityNotFoundException("Phương tiện không tồn tại"));
        vehicleRepository.delete(vehicle);
    }
}
