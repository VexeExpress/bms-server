package com.bms.bms_server.modules.ModuleAgent.dto;

import lombok.Data;

@Data
public class DTO_RQ_Agent {
    private String name;
    private String username;
    private String password;
    private Boolean status;
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
