package com.bms.bms_server.modules.ModuleOffice.dto;

import lombok.Data;

@Data
public class DTO_RP_Office {
    private Long id;
    private String officeName;
    private String officeCode;
    private Boolean status;
    private String phone;
    private String address;
}
