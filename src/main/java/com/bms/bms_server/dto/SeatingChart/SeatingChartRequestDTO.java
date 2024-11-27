package com.bms.bms_server.dto.SeatingChart;

import lombok.Data;

import java.util.List;

@Data
public class SeatingChartRequestDTO {
    private String seatingChartName;
    private Integer totalFloors;
    private Integer totalRows;
    private Integer totalColumns;
    private List<SeatRequestDTO> seats;
    private Long companyId;
}
