package com.bms.bms_server.dto.Office;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OfficeResponseDTO {
    private Long id;
    private String officeName;
    private String officeCode;
    private Boolean status;
    private String phone;
    private String address;
}
