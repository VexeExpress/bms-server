package com.bms.bms_server.modules.ModuleTrip.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
public class DTO_RP_TripInfo {
    private Long id;
    private String note;
    private LocalTime timeDeparture;
    private LocalDate dateDeparture;
    private List<String> drivers;
    private List<String> assistant;
    private String licensePlate;
    private String seatingChartName;
    private Long routeId;

    private List<String> driversDetail;
    private List<String> assistantDetail;
    private String phoneVehicle;
}
