package com.bms.bms_server.modules.ModuleOffice.entity;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "office")
@Data
public class Office {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "company_id", referencedColumnName = "id", nullable = false)
    private Company company;

    @Column(name = "office_name")
    private String officeName;

    @Column(name = "office_code")
    private String officeCode;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "phone")
    private String phone;

    @Column(name = "address")
    private String address;

}
