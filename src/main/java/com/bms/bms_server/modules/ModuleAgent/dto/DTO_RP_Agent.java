package com.bms.bms_server.modules.ModuleAgent.dto;

import lombok.Data;

import java.time.LocalDate;
@Data
public class DTO_RP_Agent {
    private Long id;
    private String name;
    private String username;
    private Boolean status;
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
