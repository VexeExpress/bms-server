package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Office;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OfficeRepository extends JpaRepository<Office, Long> {
    boolean existsByCompanyAndOfficeName(Company company, String officeName);

    List<Office> findByCompanyId(Long companyId);
}
