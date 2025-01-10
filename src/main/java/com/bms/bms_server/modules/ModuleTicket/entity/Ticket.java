package com.bms.bms_server.modules.ModuleTicket.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;
import com.bms.bms_server.modules.ModuleOffice.entity.Office;
import com.bms.bms_server.modules.ModuleTrip.entity.Trip;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "ticket")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id")
    Company company;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    Trip trip;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "id")
    Employee employee;

    @ManyToOne
    @JoinColumn(name = "office_id", referencedColumnName = "id")
    Office office;

    @Column(name = "ticket_floor")
    Integer ticketFloor;

    @Column(name = "ticket_row")
    Integer ticketRow;

    @Column(name = "ticket_column")
    Integer ticketColumn;

    @Column(name = "ticket_code")
    String ticketCode;

    @Column(name = "ticket_name")
    String ticketName;

    @Column(name = "ticket_type")
    Integer ticketType;

//    Thông tin vé
    @Column(name = "ticket_phone")
    String ticketPhone;

    @Column(name = "ticket_point_up")
    String ticketPointUp;

    @Column(name = "ticket_point_down")
    String ticketPointDown;

    @Column(name = "ticket_note")
    String ticketNote;

    @Column(name = "payment_type")
    Integer paymentType;

    @Column(name = "customer_name")
    String customerName;

    @Column(name = "ticket_price")
    Double ticketPrice;

    @Column(name = "booking_status")
    Boolean bookingStatus;

}
