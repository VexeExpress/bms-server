package com.bms.bms_server.modules.ModuleOffice.repository;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleOffice.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    boolean existsByCompanyAndOfficeName(Company company, String officeName);

    List<Office> findByCompanyId(Long companyId);
}
