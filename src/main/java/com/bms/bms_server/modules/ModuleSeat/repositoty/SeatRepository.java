package com.bms.bms_server.modules.ModuleSeat.repositoty;

import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
