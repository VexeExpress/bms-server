package com.bms.bms_server.modules.ModuleAgent.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleRoute.entity.Route;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "agent")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    Company company;

    @Column(name = "route_ids")
    private String routeIds;

    @Column(name = "name")
    String name; // Tên đại lý

    @Column(name = "code")
    String code; // Mã đại lý

    @Column(name = "username")
    String username;

    @Column(name = "password")
    String password;

    @Column(name = "status")
    Boolean status;

    @Column(name = "phone")
    String phone;

    @Column(name = "note")
    String note;

    @Column(name = "address")
    String address;

    @Column(name = "discount_ticket_type")
    Integer discountTicketType; // 1: Tính theo %, // 2: Tính theo VND

    @Column(name = "discount_ticket")
    Double discountTicket;

    @Column(name = "discount_goods_type")
    Integer discountGoodsType; // 1: Tính theo %, // 2: Tính theo VND

    @Column(name = "discount_goods")
    Double discountGoods;

    @Column(name = "created_at")
    @CreationTimestamp
    LocalDate createdAt;
}
