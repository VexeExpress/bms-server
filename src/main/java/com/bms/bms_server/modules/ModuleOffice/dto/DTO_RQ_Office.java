package com.bms.bms_server.modules.ModuleOffice.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DTO_RQ_Office {
    Long companyId;
    String officeName;
    String officeCode;
    Boolean status;
    String phone;
    String address;

}
