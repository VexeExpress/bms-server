package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatingChartRepository extends JpaRepository<SeatingChart, Long> {
    List<SeatingChart> findByCompany(Company company);

    List<SeatingChart> findByCompanyId(Long companyId);
}
