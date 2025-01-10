package com.bms.bms_server.modules.ModuleTrip.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RP_Schedule {
    Long id;
    LocalDate valueDateEnd;
    LocalDate valueDateStart;
    LocalTime valueTime;
    Long valueRoute;
    Long valueSeatChart;
    Boolean valueEnableDateEnd;
    String chart;
    String route;
    String employee;
    LocalDateTime createdAt;

}
