package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    boolean existsByLicensePlateAndCompanyId(String licensePlate, Long companyId);

    boolean existsByCompanyAndLicensePlate(Company company, String licensePlate);

    List<Vehicle> findByCompanyId(Long companyId);
}
