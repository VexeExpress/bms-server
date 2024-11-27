package com.bms.bms_server.dto.SeatingChart;

import lombok.Data;

@Data
public class SeatResponseDTO {
    private Long id;
    private Integer floor;
    private Integer row;
    private Integer column;
    private String seatCode;
    private String seatName;
    private Boolean seatStatus;

    public SeatResponseDTO() {

    }

}
