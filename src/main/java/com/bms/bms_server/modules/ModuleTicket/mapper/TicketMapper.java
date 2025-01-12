package com.bms.bms_server.modules.ModuleTicket.mapper;

import com.bms.bms_server.modules.ModuleTicket.dto.DTO_RP_Ticket;
import com.bms.bms_server.modules.ModuleTicket.dto.TicketResponseDTO;
import com.bms.bms_server.modules.ModuleTicket.entity.Ticket;

public class TicketMapper {
    public static DTO_RP_Ticket toResponseDTO(Ticket ticket) {
        DTO_RP_Ticket dto = new DTO_RP_Ticket();
        dto.setId(ticket.getId());
        dto.setFloor(ticket.getTicketFloor());
        dto.setColumn(ticket.getTicketColumn());
        dto.setRow(ticket.getTicketRow());
        dto.setCode(ticket.getTicketCode());
        dto.setName(ticket.getTicketName());

        return dto;

    }
}
