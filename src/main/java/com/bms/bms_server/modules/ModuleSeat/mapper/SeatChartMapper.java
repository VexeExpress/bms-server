package com.bms.bms_server.modules.ModuleSeat.mapper;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleSeat.dto.*;
import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;

import java.util.List;
import java.util.stream.Collectors;

public class SeatChartMapper {
    public static SeatingChart toEntity(DTO_RQ_SeatingChart dto, Company company) {
        SeatingChart seatingChart = new SeatingChart();
        seatingChart.setCompany(company);
        seatingChart.setSeatingChartName(dto.getSeatingChartName());
        seatingChart.setTotalFloors(dto.getTotalFloors());
        seatingChart.setTotalColumns(dto.getTotalColumns());
        seatingChart.setTotalRows(dto.getTotalRows());
        List<Seat> seats = dto.getSeats().stream()
                .map(SeatChartMapper::mapSeatToEntity)
                .collect(Collectors.toList());

        seatingChart.setSeats(seats);
        return seatingChart;
    }

    private static Seat mapSeatToEntity(DTO_RQ_Seat seatRequestDTO) {
        Seat seat = new Seat();
        seat.setFloor(seatRequestDTO.getFloor());
        seat.setRow(seatRequestDTO.getRow());
        seat.setColumn(seatRequestDTO.getColumn());
        seat.setSeatCode(seatRequestDTO.getSeatCode());
        seat.setSeatName(seatRequestDTO.getSeatName());
        seat.setSeatStatus(seatRequestDTO.getSeatStatus());
        return seat;
    }
    public static DTO_RP_SeatingChart toResponseDTO(SeatingChart seatingChart) {
        DTO_RP_SeatingChart responseDTO = new DTO_RP_SeatingChart();
        responseDTO.setId(seatingChart.getId());
        responseDTO.setSeatingChartName(seatingChart.getSeatingChartName());
        responseDTO.setTotalFloors(seatingChart.getTotalFloors());
        responseDTO.setTotalRows(seatingChart.getTotalRows());
        responseDTO.setTotalColumns(seatingChart.getTotalColumns());

        List<DTO_RP_Seat> seatResponseDTOS = seatingChart.getSeats().stream()
                .map(seat -> {
                    DTO_RP_Seat seatDTO = new DTO_RP_Seat();
                    seatDTO.setId(seat.getId());
                    seatDTO.setSeatCode(seat.getSeatCode());
                    seatDTO.setSeatName(seat.getSeatName());
                    seatDTO.setSeatStatus(seat.getSeatStatus());
                    seatDTO.setRow(seat.getRow());
                    seatDTO.setColumn(seat.getColumn());
                    seatDTO.setFloor(seat.getFloor());
                    return seatDTO;
                })
                .collect(Collectors.toList());

        responseDTO.setSeats(seatResponseDTOS);
        return responseDTO;
    }
    public static DTO_RP_SeatingChartName toResponseNameDTO(SeatingChart seatingChart) {
        DTO_RP_SeatingChartName responseDTO = new DTO_RP_SeatingChartName();
        responseDTO.setId(seatingChart.getId());
        responseDTO.setSeatingChartName(seatingChart.getSeatingChartName());
        return responseDTO;
    }
}
