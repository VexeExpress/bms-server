package com.bms.bms_server.dto.Agent;

import lombok.Data;

import java.time.LocalDate;
@Data
public class AgentResponseDTO {
    private Long id;
    private String name;
    private String code;
    private String phone;
    private String note;
    private String address;
    private Integer discountTicketType;
    private Double discountTicket;
    private Integer discountGoodsType;
    private Double discountGoods;
    private LocalDate createdAt;
}
