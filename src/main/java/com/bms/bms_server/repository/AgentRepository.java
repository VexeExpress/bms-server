package com.bms.bms_server.repository;

import com.bms.bms_server.entity.Agent;
import com.bms.bms_server.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

    List<Agent> findByCompanyId(Long companyId);

    boolean existsByCompanyAndName(Company company, String name);
}
