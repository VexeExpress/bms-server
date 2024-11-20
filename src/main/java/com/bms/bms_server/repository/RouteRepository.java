package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RouteRepository extends JpaRepository<Route, Long> {
    boolean existsByCompanyAndRouteName(Company company, String routeName);

    List<Route> findByCompanyId(Long companyId);

    @Query("SELECT MAX(r.displayOrder) FROM Route r WHERE r.company = :company")
    Optional<Integer> findMaxDisplayOrderByCompany(@Param("company") Company company);

    @Query("SELECT r FROM Route r WHERE r.company.id = :companyId ORDER BY r.displayOrder ASC")
    List<Route> findByCompanyIdOrderByDisplayOrderAsc(@Param("companyId") Long companyId);


    Optional<Route> findByCompanyIdAndDisplayOrder(Long companyId, int i);
}
