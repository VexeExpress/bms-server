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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfficeService {
    @Autowired
    OfficeRepository officeRepository;
    @Autowired
    CompanyRepository companyRepository;

    // PB.02_US.01: Add new office
    public DTO_RP_Office createOffice(DTO_RQ_Office dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getOfficeName() == null || dto.getOfficeName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_OFFICE_NAME);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        boolean exists = officeRepository.existsByCompanyAndOfficeName(company, dto.getOfficeName());
        if (exists) {
            throw new AppException(ErrorCode.OFFICE_ALREADY_EXISTED);
        }
        Office office = OfficeMapper.toEntity(dto, company);
        Office savedOffice = officeRepository.save(office);
        return OfficeMapper.toResponseDTO(savedOffice);
    }

    // PB.02_US.02: Update office information
    public DTO_RP_Office updateOffice(Long officeId, DTO_RQ_Office dto) {
        Office office = officeRepository.findById(officeId)
                .orElseThrow(() -> new AppException(ErrorCode.OFFICE_NOT_FOUND));
        if (dto.getOfficeName() == null || dto.getOfficeName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_OFFICE_NAME);
        }

        office.setOfficeName(dto.getOfficeName());
        office.setOfficeCode(dto.getOfficeCode());
        office.setAddress(dto.getAddress());
        office.setPhone(dto.getPhone());
        office.setStatus(dto.getStatus());

        Office updatedOffice = officeRepository.save(office);
        return OfficeMapper.toResponseDTO(updatedOffice);
    }

    // PB.02_US.03: Remove office
    public void deleteOfficeById(Long id) {
        Optional<Office> officeOptional = officeRepository.findById(id);
        if (officeOptional.isEmpty()) {
            throw new AppException(ErrorCode.OFFICE_NOT_FOUND);
        }
        officeRepository.deleteById(id);
    }

    // PB.02_US.04: Filter/Get list office
    public List<DTO_RP_Office> getListOfficeByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .map(OfficeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // PB.02_US.05: Filter/Get list office name
    public List<DTO_RP_OfficeName> getListOfficeNameByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .filter(Office::getStatus)
                .map(OfficeMapper::toOfficeNameResponseDTO)
                .collect(Collectors.toList());
    }


}
