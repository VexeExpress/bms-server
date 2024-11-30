package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Trip.TripInfoResponseDTO;
import com.bms.bms_server.entity.Trip;

import java.util.List;
import java.util.stream.Collectors;

public class TripMapper {
    public static TripInfoResponseDTO toResponseDTO(Trip trip) {
        TripInfoResponseDTO dto = new TripInfoResponseDTO();
        dto.setId(trip.getId());
        dto.setTimeDeparture(trip.getTimeDeparture());
        dto.setDateDeparture(trip.getDateDeparture());
        dto.setNote(trip.getNote());
        dto.setSeatingChartName(trip.getSeatChart().getSeatingChartName());
        dto.setLicensePlate(trip.getVehicle().getLicensePlate());
        List<String> driverNames = trip.getDrivers().stream()
                .map(driver -> driver.getFullName())
                .collect(Collectors.toList());
        dto.setDrivers(driverNames);
        List<String> assistantNames = trip.getAssistant().stream()
                .map(assistant -> assistant.getFullName())
                .collect(Collectors.toList());
        dto.setAssistant(assistantNames);
        List<String> driversDetail = trip.getDrivers().stream()
                .map(driver -> driver.getFullName() + " (" + driver.getPhone() + ")")
                .collect(Collectors.toList());
        dto.setDriversDetail(driversDetail);
        List<String> assistantDetail = trip.getAssistant().stream()
                .map(assistant -> assistant.getFullName() + " (" + assistant.getPhone() + ")")
                .collect(Collectors.toList());
        dto.setAssistantDetail(assistantDetail);
        dto.setPhoneVehicle(trip.getVehicle().getPhone());
        dto.setRouteId(trip.getRoute().getId());

        return dto;
    }

}