package com.bms.bms_server.modules.ModuleTrip.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleVehicle.entity.Vehicle;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "trip")
@Data
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id", nullable = false)
    private Route route;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id", nullable = true)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "seat_chart_id", referencedColumnName = "id")
    private SeatingChart seatChart;

    @ManyToMany
    @JoinTable(
            name = "trip_driver",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> drivers;

    @ManyToMany
    @JoinTable(
            name = "trip_assistant",
            joinColumns = @JoinColumn(name = "trip_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id")
    )
    private List<Employee> assistant;

    @Column(name = "time_departure", nullable = false)
    private LocalTime timeDeparture;

    @Column(name = "date_departure", nullable = false)
    private LocalDate dateDeparture;

    @Column(name = "note")
    private String note;
}
