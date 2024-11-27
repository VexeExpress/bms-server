package com.bms.bms_server.dto.Trip;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
@Data
public class TripRequestDTO {
    private Long companyId;
    private Long routeId;
    private Long vehicleId;
    private List<Long> driverIds;
    private List<Long> assistantIds;
    private LocalTime timeDeparture;
    private LocalDate dateDeparture;
    private String note;
}
