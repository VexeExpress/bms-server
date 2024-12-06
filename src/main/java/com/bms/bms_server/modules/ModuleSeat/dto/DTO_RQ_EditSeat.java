package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.Data;

@Data
public class DTO_RQ_EditSeat {
    private Integer floor;
    private Integer row;
    private Integer column;
    private String seatCode;
    private String seatName;
    private Boolean seatStatus;
}
