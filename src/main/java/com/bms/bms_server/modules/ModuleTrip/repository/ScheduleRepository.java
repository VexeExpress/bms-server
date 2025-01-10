package com.bms.bms_server.modules.ModuleTrip.repository;

import com.bms.bms_server.modules.ModuleTrip.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findByCompanyId(Long companyId);

    List<Schedule> findByCompanyIdAndRouteIdAndDateStartLessThanEqualAndDateEndGreaterThanEqual(
            Long companyId, Long routeId, LocalDate dateStart, LocalDate dateEnd);
}
