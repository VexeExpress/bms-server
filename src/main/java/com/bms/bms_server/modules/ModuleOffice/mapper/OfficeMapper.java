package com.bms.bms_server.modules.ModuleOffice.mapper;

import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_OfficeName;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RQ_Office;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_Office;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleOffice.entity.Office;

public class OfficeMapper {
    public static Office toEntity(DTO_RQ_Office dto, Company company) {
        Office office = new Office();
        office.setCompany(company);
        office.setOfficeName(dto.getOfficeName());
        office.setOfficeCode(dto.getOfficeCode());
        office.setPhone(dto.getPhone());
        office.setAddress(dto.getAddress());
        office.setStatus(dto.getStatus());
        return office;
    }
    public static DTO_RP_Office toResponseDTO(Office office) {
        DTO_RP_Office dto = new DTO_RP_Office();
        dto.setId(office.getId());
        dto.setOfficeName(office.getOfficeName());
        dto.setOfficeCode(office.getOfficeCode());
        dto.setPhone(office.getPhone());
        dto.setAddress(office.getAddress());
        dto.setStatus(office.getStatus());
        return dto;
    }
    public static DTO_RP_OfficeName toOfficeNameResponseDTO(Office office) {
        DTO_RP_OfficeName dto = new DTO_RP_OfficeName();
        dto.setId(office.getId());
        dto.setOfficeName(office.getOfficeName());
        return dto;
    }
}
