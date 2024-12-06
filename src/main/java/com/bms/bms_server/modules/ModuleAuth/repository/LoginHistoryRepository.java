package com.bms.bms_server.modules.ModuleAuth.repository;

import com.bms.bms_server.modules.ModuleAuth.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByCompanyId(Long companyId);
}
