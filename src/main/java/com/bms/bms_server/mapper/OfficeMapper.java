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
        office.setOfficeCode(dto.getOfficeCode());
        office.setPhone(dto.getPhone());
        office.setAddress(dto.getAddress());
        office.setStatus(dto.getStatus());
        return office;
    }
    public static OfficeResponseDTO toResponseDTO(Office office) {
        OfficeResponseDTO dto = new OfficeResponseDTO();
        dto.setId(office.getId());
        dto.setOfficeName(office.getOfficeName());
        dto.setOfficeCode(office.getOfficeCode());
        dto.setPhone(office.getPhone());
        dto.setAddress(office.getAddress());
        dto.setStatus(office.getStatus());
        return dto;
    }
}
