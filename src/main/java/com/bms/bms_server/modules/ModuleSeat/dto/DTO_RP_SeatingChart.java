package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTO_RP_SeatingChart {
    private Long id;
    private String seatingChartName;
    private Integer totalFloors;
    private Integer totalRows;
    private Integer totalColumns;
    private List<DTO_RP_Seat> seats;


    public DTO_RP_SeatingChart() {

    }
}
