package com.bms.bms_server.dto.Ticket;

import lombok.Data;

import java.util.List;
@Data
public class UpdateTicketsRequestDTO {
    private List<Long> ticketIds;
    private UpdateTicketResquestDTO commonData;
}
