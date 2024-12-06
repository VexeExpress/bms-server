package com.bms.bms_server.modules.ModuleTicket.mapper;

import com.bms.bms_server.modules.ModuleTicket.dto.TicketResponseDTO;
import com.bms.bms_server.modules.ModuleTicket.entity.Ticket;

public class TicketMapper {
    public static TicketResponseDTO toResponseDTO(Ticket ticket) {
        TicketResponseDTO dto = new TicketResponseDTO();
        dto.setId(ticket.getId());
        dto.setTicketColumn(ticket.getTicketColumn());
        dto.setTicketRow(ticket.getTicketRow());
        dto.setTicketFloor(ticket.getTicketFloor());
        dto.setTicketCode(ticket.getTicketCode());
        dto.setTicketName(ticket.getTicketName());
        dto.setTicketStatus(ticket.getTicketStatus());

        dto.setTicketPhone(ticket.getTicketPhone());
        dto.setTicketPointUp(ticket.getTicketPointUp());
        dto.setTicketPointDown(ticket.getTicketPointDown());
        dto.setTicketNote(ticket.getTicketNote());
        dto.setPaymentType(ticket.getPaymentType());
        dto.setCustomerName(ticket.getCustomerName());
        dto.setTicketPrice(ticket.getTicketPrice());
        dto.setEmployeeName(ticket.getEmployee() != null ? ticket.getEmployee().getFullName() : "");
        dto.setOfficeName(ticket.getOffice() != null ? ticket.getOffice().getOfficeName() : "");
        dto.setBookingStatus(ticket.getBookingStatus());
        return dto;

    }
}
