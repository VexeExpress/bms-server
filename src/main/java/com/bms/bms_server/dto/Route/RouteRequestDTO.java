package com.bms.bms_server.dto.Route;

import lombok.Data;

@Data
public class RouteRequestDTO {
    private String routeName;
    private String routeNameShort;
    private Double displayPrice;
    private Boolean status;
    private String note;
    private Integer displayOrder;
    private Long companyId;
}
