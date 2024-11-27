package com.bms.bms_server.dto.SeatingChart;

import lombok.Data;

import java.util.List;
@Data
public class EditSeatChartDTO {
    private String seatingChartName;
    private Integer totalFloors;
    private Integer totalRows;
    private Integer totalColumns;
    private List<EditSeatDTO> seats;
}
