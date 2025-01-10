package com.bms.bms_server.modules.ModuleTrip.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "trip_schedule")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    Route route;

    @ManyToOne
    @JoinColumn(name = "chart_id", referencedColumnName = "id")
    SeatingChart chart;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;

    @Column(name = "date_start")
    LocalDate dateStart;

    @Column(name = "enable_date_end")
    Boolean enableDateEnd;

    @Column(name = "date_end")
    LocalDate dateEnd;

    @Column(name = "time")
    LocalTime time;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;
}
