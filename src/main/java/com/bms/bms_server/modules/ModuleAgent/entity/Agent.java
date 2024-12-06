package com.bms.bms_server.modules.ModuleAgent.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;

@Entity
@Table(name = "agent")
@Data
public class Agent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "name")
    private String name; // Tên đại lý

    @Column(name = "code")
    private String code; // Mã đại lý

    @Column(name = "phone")
    private String phone;

    @Column(name = "note")
    private String note;

    @Column(name = "address")
    private String address;

    @Column(name = "discount_ticket_type")
    private Integer discountTicketType; // 1: Tính theo %, // 2: Tính theo VND

    @Column(name = "discount_ticket")
    private Double discountTicket;

    @Column(name = "discount_goods_type")
    private Integer discountGoodsType; // 1: Tính theo %, // 2: Tính theo VND

    @Column(name = "discount_goods")
    private Double discountGoods;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDate createdAt;
}
