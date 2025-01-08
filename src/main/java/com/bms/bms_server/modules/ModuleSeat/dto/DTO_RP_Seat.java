package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.Data;

@Data
public class DTO_RP_Seat {
    private Long id;
    private Integer floor;
    private Integer row;
    private Integer column;
    private String code;
    private String name;
    private Integer type;


}
