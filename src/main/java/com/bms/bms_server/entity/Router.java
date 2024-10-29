package com.bms.bms_server.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "router")
@Data
public class Router {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "router_name")
    private String routerName;

    @Column(name = "route_name_short")
    private String routeNameShort;

    @Column(name = "display_price")
    private Double displayPrice;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "note")
    private String note;
}
