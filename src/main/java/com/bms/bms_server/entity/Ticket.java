package com.bms.bms_server.entity;

import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id", nullable = false)
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = true)
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id", nullable = true)
    private Office office;

    @Column(name = "ticket_floor", nullable = false)
    private Integer ticketFloor;

    @Column(name = "ticket_row", nullable = false)
    private Integer ticketRow;

    @Column(name = "ticket_column", nullable = false)
    private Integer ticketColumn;

    @Column(name = "ticket_code", nullable = false)
    private String ticketCode;

    @Column(name = "ticket_name", nullable = false)
    private String ticketName;

    @Column(name = "ticket_status", nullable = false)
    private Boolean ticketStatus;

//    Thông tin vé
    @Column(name = "ticket_phone")
    private String ticketPhone;

    @Column(name = "ticket_point_up")
    private String ticketPointUp;

    @Column(name = "ticket_point_down")
    private String ticketPointDown;

    @Column(name = "ticket_note")
    private String ticketNote;

    @Column(name = "payment_type")
    private Integer paymentType;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "ticket_price")
    private Double ticketPrice;

    @Column(name = "booking_status", nullable = false)
    private Boolean bookingStatus = false;

}
