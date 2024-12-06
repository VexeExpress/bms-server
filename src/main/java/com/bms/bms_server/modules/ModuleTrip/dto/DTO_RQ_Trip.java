package com.bms.bms_server.modules.ModuleTrip.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class DTO_RQ_Trip {
    private Long companyId;
    private Long routeId;
    private Long vehicleId;
    private Long seatChartId;
    private List<Long> driverIds;
    private List<Long> assistantIds;
    private LocalTime timeDeparture;
    private LocalDate dateDeparture;
    private String note;
}
