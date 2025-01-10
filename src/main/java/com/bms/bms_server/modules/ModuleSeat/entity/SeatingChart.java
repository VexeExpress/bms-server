package com.bms.bms_server.modules.ModuleSeat.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "seating_chart")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatingChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;

    @Column(name = "seat_chart_name")
    String seatChartName;

    @Column(name = "total_floor")
    Integer totalFloor;

    @Column(name = "total_row")
    Integer totalRow;

    @Column(name = "total_column")
    Integer totalColumn;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "seating_chart_id")
    List<Seat> seats;

}
