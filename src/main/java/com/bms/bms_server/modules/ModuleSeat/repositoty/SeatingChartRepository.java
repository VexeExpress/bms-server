package com.bms.bms_server.modules.ModuleSeat.repositoty;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatingChartRepository extends JpaRepository<SeatingChart, Long> {
    List<SeatingChart> findByCompany(Company company);

    List<SeatingChart> findByCompanyId(Long companyId);

    boolean existsByCompanyAndSeatChartName(Company company, String name);
}
