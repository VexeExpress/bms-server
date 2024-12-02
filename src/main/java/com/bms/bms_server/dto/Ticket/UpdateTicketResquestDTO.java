package com.bms.bms_server.dto.Ticket;

import lombok.Data;

@Data
public class UpdateTicketResquestDTO {
    private String ticketPointDown;
    private String customerName;
    private String ticketNote;
    private Integer paymentType;
    private String ticketPhone;
    private String ticketPointUp;
    private Double ticketPrice;
    private Long officeId;
    private Long employeeId;
}
