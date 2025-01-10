package com.bms.bms_server.modules.ModuleTrip.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RQ_Schedule {
    Long id;
    LocalDate valueDateEnd;
    LocalDate valueDateStart;
    Long valueRoute;
    Long valueSeatChart;
    LocalTime valueTime;
    Boolean valueEnableDateEnd;
    Long companyId;
    Long employeeId;
}
