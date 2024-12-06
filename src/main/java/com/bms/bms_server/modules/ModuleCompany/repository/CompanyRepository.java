package com.bms.bms_server.modules.ModuleCompany.repository;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
}
