package com.bms.bms_server.repository;

import com.bms.bms_server.entity.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
    List<LoginHistory> findByCompanyId(Long companyId);
}
