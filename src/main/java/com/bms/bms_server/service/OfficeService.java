package com.bms.bms_server.service;

import com.bms.bms_server.dto.Office.OfficeNameResponseDTO;
import com.bms.bms_server.dto.Office.OfficeRequestDTO;
import com.bms.bms_server.dto.Office.OfficeResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Office;
import com.bms.bms_server.mapper.OfficeMapper;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.OfficeRepository;
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
    public OfficeResponseDTO createOffice(OfficeRequestDTO dto) {
        System.out.println(dto);
        if (dto.getCompanyId() == null) {
            throw new IllegalArgumentException("Company ID is required.");
        }
        if (dto.getOfficeName() == null || dto.getOfficeName().trim().isEmpty()) {
            throw new IllegalArgumentException("Office name is required.");
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new EntityNotFoundException("Company not found."));

        boolean exists = officeRepository.existsByCompanyAndOfficeName(company, dto.getOfficeName());
        if (exists) {
            throw new IllegalArgumentException("Office name already exists for this company.");
        }

        Office office = OfficeMapper.toEntity(dto, company);

        Office savedOffice = officeRepository.save(office);
        System.out.println("Saved Office: " + savedOffice);
        return OfficeMapper.toResponseDTO(savedOffice);
    }

    public List<OfficeResponseDTO> getListOfficeByCompanyId(Long companyId) {
        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .map(OfficeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public OfficeResponseDTO updateOffice(Long officeId, OfficeRequestDTO updatedData) {
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

    public List<OfficeNameResponseDTO> getListOfficeNameByCompanyId(Long companyId) {
        List<Office> offices = officeRepository.findByCompanyId(companyId);
        return offices.stream()
                .map(OfficeMapper::toOfficeNameResponseDTO)
                .collect(Collectors.toList());
    }
}
