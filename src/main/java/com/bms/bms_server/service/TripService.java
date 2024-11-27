package com.bms.bms_server.service;

import com.bms.bms_server.dto.Trip.TripRequestDTO;
import com.bms.bms_server.entity.*;
import com.bms.bms_server.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;
    @Autowired
    RouteRepository routeRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    public Trip createTrip(TripRequestDTO tripRequestDTO) {
        Company company = companyRepository.findById(tripRequestDTO.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));
        Route route = routeRepository.findById(tripRequestDTO.getRouteId())
                .orElseThrow(() -> new RuntimeException("Route not found"));
        Vehicle vehicle = vehicleRepository.findById(tripRequestDTO.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        // Lấy danh sách tài xế và phụ xe
        List<Employee> drivers = employeeRepository.findAllById(tripRequestDTO.getDriverIds());
        List<Employee> assistants = employeeRepository.findAllById(tripRequestDTO.getAssistantIds());

        Trip trip = new Trip();
        trip.setCompany(company);
        trip.setRoute(route);
        trip.setVehicle(vehicle);
        trip.setDrivers(drivers); // Thêm tài xế
        trip.setAssistant(assistants); // Thêm phụ xe
        trip.setTimeDeparture(tripRequestDTO.getTimeDeparture());
        trip.setDateDeparture(tripRequestDTO.getDateDeparture());
        trip.setNote(tripRequestDTO.getNote());

        return tripRepository.save(trip);
    }
}
