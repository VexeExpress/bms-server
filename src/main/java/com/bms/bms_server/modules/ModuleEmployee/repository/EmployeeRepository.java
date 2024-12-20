package com.bms.bms_server.modules.ModuleEmployee.repository;

import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import lombok.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    boolean existsByUsernameAndCompanyId(String username, Long companyId);

    List<Employee> findByCompanyId(Long companyId);

    boolean existsByUsernameAndCompanyIdAndIdNot(String username, Long companyId, Long id);
    Optional<Employee> findByUsername(String username);


    List<Employee> findByFullNameContainingAndCompanyId(String fullName, Long companyId);

//    List<Employee> findByRoleAndCompanyId(Integer role, Long companyId);
}
