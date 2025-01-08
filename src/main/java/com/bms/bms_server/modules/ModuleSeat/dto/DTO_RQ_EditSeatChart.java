package com.bms.bms_server.modules.ModuleSeat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DTO_RQ_EditSeatChart {
    Long id;
    String name;
    Integer floor;
    Integer row;
    Integer column;
    List<DTO_RQ_EditSeat> seats;
}
