package com.bms.bms_server.service;

import com.bms.bms_server.dto.Vehicle.request.VehicleRequestDTO;
import com.bms.bms_server.dto.Vehicle.response.VehicleResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Vehicle;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;

    public boolean licensePlateExists(String licensePlate, Long companyId) {
        return vehicleRepository.existsByLicensePlateAndCompanyId(licensePlate, companyId);
    }

    public VehicleResponseDTO createVehicle(VehicleRequestDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new IllegalArgumentException("Company not found"));
        Vehicle newVehicle = new Vehicle();
        newVehicle.setLicensePlate(dto.getLicensePlate());
        newVehicle.setPhone(dto.getPhone());
        newVehicle.setColor(dto.getColor());
        newVehicle.setBrand(dto.getBrand());
        newVehicle.setChassisNumber(dto.getChassisNumber());
        newVehicle.setEngineNumber(dto.getEngineNumber());
        newVehicle.setType(dto.getType());
        newVehicle.setNumberOfSeat(dto.getNumberOfSeat());
        newVehicle.setRegistrationPeriod(dto.getRegistrationPeriod());
        newVehicle.setInsurancePeriod(dto.getInsurancePeriod());
        newVehicle.setCompany(company);

        Vehicle savedVehicle = vehicleRepository.save(newVehicle);
        return mapToResponseDTO(savedVehicle);
    }

    private VehicleResponseDTO mapToResponseDTO(Vehicle savedVehicle) {
        VehicleResponseDTO dto = new VehicleResponseDTO();
        dto.setId(savedVehicle.getId());
        dto.setLicensePlate(savedVehicle.getLicensePlate());
        dto.setPhone(savedVehicle.getPhone());
        dto.setColor(savedVehicle.getColor());
        dto.setBrand(savedVehicle.getBrand());
        dto.setChassisNumber(savedVehicle.getChassisNumber());
        dto.setEngineNumber(savedVehicle.getEngineNumber());
        dto.setType(savedVehicle.getType());
        dto.setNumberOfSeat(savedVehicle.getNumberOfSeat());
        dto.setRegistrationPeriod(savedVehicle.getRegistrationPeriod());
        dto.setInsurancePeriod(savedVehicle.getInsurancePeriod());
        return dto;
    }
}
