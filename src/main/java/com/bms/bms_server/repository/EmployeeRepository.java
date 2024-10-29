package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsernameAndCompanyId(String username, Long companyId);
}
