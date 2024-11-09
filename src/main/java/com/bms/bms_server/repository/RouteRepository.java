package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsByCompanyAndRouteName(Company company, String routeName);

    List<Route> findByCompanyId(Long companyId);
}
