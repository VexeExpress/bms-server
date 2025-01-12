package com.bms.bms_server.modules.ModuleTrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RP_TripData {
    Long id;
    LocalTime timeDeparture;
    LocalDate dateDeparture;
    String note;
    Long seatChartId;
    Long vehicleId;
    String seatChartName;
    String licensePlate;
    String routeName;

}
