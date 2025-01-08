package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.Data;

import java.util.List;

@Data
public class DTO_RP_SeatingChart {
    private Long id;
    private String name;
    private Integer floor;
    private Integer row;
    private Integer column;
    private List<DTO_RP_Seat> seats;


    public DTO_RP_SeatingChart() {

    }
}
