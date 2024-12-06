package com.bms.bms_server.modules.ModuleRoute.dto;

import lombok.Data;

@Data
public class DTO_RQ_Route {
    private String routeName;
    private String routeNameShort;
    private Double displayPrice;
    private Boolean status;
    private String note;
    private Integer displayOrder;
    private Long companyId;
}
