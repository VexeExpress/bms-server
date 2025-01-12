package com.bms.bms_server.modules.ModuleTicket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RP_Ticket {
    Long id;
    Integer floor;
    Integer column;
    Integer row;
    String code;
    String name;
}
