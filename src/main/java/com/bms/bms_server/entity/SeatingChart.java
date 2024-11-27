package com.bms.bms_server.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "seating_chart")
@Data
public class SeatingChart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "chart_name", nullable = false)
    private String seatingChartName;

    @Column(name = "total_floors", nullable = false)
    private Integer totalFloors;

    @Column(name = "total_rows", nullable = false)
    private Integer totalRows;

    @Column(name = "total_columns", nullable = false)
    private Integer totalColumns;

    @OneToMany(mappedBy = "seatingChart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;
}
