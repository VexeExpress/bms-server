package com.bms.bms_server.modules.ModuleSeat.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "seat")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "seat_code")
    String seatCode;

    @Column(name = "seat_name")
    String seatName;

    @Column(name = "seat_type")
    Integer seatType;

    @Column(name = "seat_floor")
    Integer seatFloor;

    @Column(name = "seat_row")
    Integer seatRow;

    @Column(name = "seat_column")
    Integer seatColumn;
}
