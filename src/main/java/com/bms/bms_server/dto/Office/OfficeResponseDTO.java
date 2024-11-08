package com.bms.bms_server.dto.Office;

import lombok.Data;

import java.time.LocalDate;

@Data
public class OfficeResponseDTO {
    private Long id;
    private String officeName;
    private LocalDate createdAt;


    public OfficeResponseDTO() {

    }

    public OfficeResponseDTO(Long id, String officeName, LocalDate createdAt) {
    }
}
