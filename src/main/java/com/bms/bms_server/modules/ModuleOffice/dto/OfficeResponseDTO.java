package com.bms.bms_server.modules.ModuleOffice.dto;

import lombok.Data;

@Data
public class OfficeResponseDTO {
    private Long id;
    private String officeName;
    private String officeCode;
    private Boolean status;
    private String phone;
    private String address;
}
