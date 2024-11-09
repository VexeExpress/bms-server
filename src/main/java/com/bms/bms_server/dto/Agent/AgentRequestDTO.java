package com.bms.bms_server.dto.Agent;

import lombok.Data;

@Data
public class AgentRequestDTO {
    private String name;
    private String code;
    private String phone;
    private String note;
    private String address;
    private Integer discountTicketType;
    private Double discountTicket;
    private Integer discountGoodsType;
    private Double discountGoods;
    private Long companyId;
}
