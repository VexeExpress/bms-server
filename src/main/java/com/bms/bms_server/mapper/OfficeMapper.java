package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Office.OfficeRequestDTO;
import com.bms.bms_server.dto.Office.OfficeResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Office;

public class OfficeMapper {
    public static Office toEntity(OfficeRequestDTO dto, Company company) {
        Office office = new Office();
        office.setCompany(company);
        office.setOfficeName(dto.getOfficeName());
        return office;
    }
    public static OfficeResponseDTO toResponseDTO(Office office) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setId(office.getId());
        dto.setOfficeName(office.getOfficeName());
        dto.setCreatedAt(office.getCreatedAt());
        return dto;
    }
}
