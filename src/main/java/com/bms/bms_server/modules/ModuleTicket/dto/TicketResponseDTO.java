package com.bms.bms_server.modules.ModuleTicket.dto;

import lombok.Data;

@Data
public class TicketResponseDTO {
    private Long id;
    private Integer ticketFloor;
    private Integer ticketRow;
    private Integer ticketColumn;
    private String ticketCode;
    private String ticketName;
    private Boolean ticketStatus;

    private String ticketPhone;
    private String ticketPointUp;
    private String ticketPointDown;
    private String ticketNote;
    private Integer paymentType;
    private String customerName;
    private Double ticketPrice;
    private String employeeName;
    private String officeName;
    private Boolean bookingStatus;
}
