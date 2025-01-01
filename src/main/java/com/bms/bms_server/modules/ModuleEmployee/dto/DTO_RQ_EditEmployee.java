package com.bms.bms_server.modules.ModuleEmployee.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Set;

@Data

@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DTO_RQ_EditEmployee {
    String fullName;
    String phone;
    LocalDate startDate;
    LocalDate birthDate;
    Integer gender;
    String email;
    String address;
    Boolean status;
    Set<String> roles;
    Boolean accessBms;
    Boolean accessCms;
    Boolean accessTms;
    Long companyId;
}
