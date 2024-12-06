package com.bms.bms_server.modules.ModuleOffice.mapper;

import com.bms.bms_server.modules.ModuleOffice.dto.OfficeNameResponseDTO;
import com.bms.bms_server.modules.ModuleOffice.dto.OfficeRequestDTO;
import com.bms.bms_server.modules.ModuleOffice.dto.OfficeResponseDTO;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleOffice.entity.Office;

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
    public static OfficeNameResponseDTO toOfficeNameResponseDTO(Office office) {
        OfficeNameResponseDTO dto = new OfficeNameResponseDTO();
        dto.setId(office.getId());
        dto.setOfficeName(office.getOfficeName());
        return dto;
    }
}
