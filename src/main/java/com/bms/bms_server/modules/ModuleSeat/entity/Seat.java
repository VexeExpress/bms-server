package com.bms.bms_server.modules.ModuleSeat.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "seats")
@Data
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_code", nullable = false)
    private String seatCode;

    @Column(name = "seat_name", nullable = false)
    private String seatName;

    @Column(name = "seat_status", nullable = false)
    private Boolean seatStatus;

    @Column(name = "seat_floor", nullable = false)
    private Integer floor;

    @Column(name = "seat_row", nullable = false)
    private Integer row;

    @Column(name = "seat_column", nullable = false)
    private Integer column;

//    @ManyToOne
//    @JoinColumn(name = "seating_chart_id", referencedColumnName = "id")
//    private SeatingChart seatingChart;

}
