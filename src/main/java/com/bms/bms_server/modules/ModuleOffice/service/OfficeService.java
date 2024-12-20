package com.bms.bms_server.modules.ModuleOffice.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_OfficeName;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RQ_Office;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_Office;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleOffice.entity.Office;
import com.bms.bms_server.modules.ModuleOffice.mapper.OfficeMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleOffice.repository.OfficeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    CompanyRepository companyRepository;

    public DTO_RP_Office createOffice(DTO_RQ_Office dto) {
        System.out.println(dto);
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.COMPANY_NOT_EXIST);
        }
        if (dto.getOfficeName() == null || dto.getOfficeName().trim().isEmpty()) {
            throw new AppException(ErrorCode.OFFICE_NAME_REQUIRED);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        boolean exists = officeRepository.existsByCompanyAndOfficeName(company, dto.getOfficeName());
        if (exists) {
            throw new AppException(ErrorCode.OFFICE_EXISTED);
        }

        Office office = OfficeMapper.toEntity(dto, company);

        Office savedOffice = officeRepository.save(office);
        System.out.println("Saved Office: " + savedOffice);
        return OfficeMapper.toResponseDTO(savedOffice);
    }

    public List<DTO_RP_Office> getListOfficeByCompanyId(Long companyId) {
        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .map(OfficeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public DTO_RP_Office updateOffice(Long officeId, DTO_RQ_Office updatedData) {
        System.out.println(updatedData);
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException("Office with ID " + officeId + " not found"));
        if (updatedData.getOfficeName() != null) {
            office.setOfficeName(updatedData.getOfficeName());
            office.setOfficeCode(updatedData.getOfficeCode());
            office.setAddress(updatedData.getAddress());
            office.setPhone(updatedData.getPhone());
            office.setStatus(updatedData.getStatus());
        }
        Office updatedOffice = officeRepository.save(office);
        return OfficeMapper.toResponseDTO(updatedOffice);
    }

    public void deleteOfficeById(Long officeId) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new EntityNotFoundException("Văn phòng không tồn tại"));
        officeRepository.delete(office);
    }

    public List<DTO_RP_OfficeName> getListOfficeNameByCompanyId(Long companyId) {
        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .map(OfficeMapper::toOfficeNameResponseDTO)
                .collect(Collectors.toList());
    }
}
