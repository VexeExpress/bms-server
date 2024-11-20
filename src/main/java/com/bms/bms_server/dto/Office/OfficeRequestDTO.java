package com.bms.bms_server.dto.Office;

import lombok.Data;

@Data
public class OfficeRequestDTO {
    private Long companyId;
    private String officeName;
    private String officeCode;
    private Boolean status;
    private String phone;
    private String address;

}
