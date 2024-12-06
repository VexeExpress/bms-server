package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTO_RQ_SeatingChart {
    private String seatingChartName;
    private Integer totalFloors;
    private Integer totalRows;
    private Integer totalColumns;
    private List<DTO_RQ_Seat> seats;
    private Long companyId;
}
