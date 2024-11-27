package com.bms.bms_server.dto.SeatingChart;

import lombok.Data;

@Data
public class EditSeatDTO {
    private Integer floor;
    private Integer row;
    private Integer column;
    private String seatCode;
    private String seatName;
    private Boolean seatStatus;
}
