package com.bms.bms_server.dto.Ticket;

import lombok.Data;

@Data
public class UpdateTicketResquestDTO {
    private String dropoff;
    private String name;
    private String note;
    private Integer paymentType;
    private String phone;
    private String pickup;
    private Double price;
    private Long officeId;
    private Long employeeId;
}
