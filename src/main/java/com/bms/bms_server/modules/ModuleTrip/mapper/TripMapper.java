package com.bms.bms_server.modules.ModuleTrip.mapper;

import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripData;
import com.bms.bms_server.modules.ModuleTrip.dto.DTO_RP_TripInfo;
import com.bms.bms_server.modules.ModuleTrip.entity.Trip;

import java.util.List;
import java.util.stream.Collectors;

public class TripMapper {
    public static DTO_RP_TripData toResponseDTO(Trip trip) {
        DTO_RP_TripData dto = new DTO_RP_TripData();
        dto.setId(trip.getId());
        dto.setTimeDeparture(trip.getTimeDeparture());
        dto.setDateDeparture(trip.getDateDeparture());
        dto.setNote(trip.getNote());
        dto.setSeatChartId(trip.getSeatChart().getId());
        dto.setSeatChartName(trip.getSeatChart().getSeatChartName());
        if (trip.getVehicle() != null) {
            dto.setVehicleId(trip.getVehicle().getId());
            dto.setLicensePlate(trip.getVehicle().getLicensePlate());
        } else {
            dto.setVehicleId(null);
            dto.setLicensePlate(null);
        }
        dto.setRouteName(trip.getRoute().getRouteName());


        return dto;
    }

}
