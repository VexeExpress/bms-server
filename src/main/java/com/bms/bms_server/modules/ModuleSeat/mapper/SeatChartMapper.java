package com.bms.bms_server.modules.ModuleSeat.mapper;

import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleSeat.dto.*;
import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;

import java.util.List;
import java.util.stream.Collectors;

public class SeatChartMapper {
    public static SeatingChart toEntity(DTO_RQ_CreateSeatChart dto, Company company) {
        SeatingChart seatingChart = new SeatingChart();
        seatingChart.setCompany(company);
        seatingChart.setTotalFloor(dto.getFloor());
        seatingChart.setTotalColumn(dto.getColumn());
        seatingChart.setTotalRow(dto.getRow());
        seatingChart.setSeatChartName(dto.getName());
        List<Seat> seats = dto.getSeats().stream()
                .map(SeatChartMapper::mapSeatToEntity)
                .collect(Collectors.toList());
        seatingChart.setSeats(seats);
        return seatingChart;
    }

    private static Seat mapSeatToEntity(DTO_RQ_CreateSeat dtoSeat) {
        Seat seat = new Seat();
        seat.setSeatCode(dtoSeat.getCode());
        seat.setSeatColumn(dtoSeat.getColumn());
        seat.setSeatFloor(dtoSeat.getFloor());
        seat.setSeatName(dtoSeat.getName());
        seat.setSeatRow(dtoSeat.getRow());
        seat.setSeatType(dtoSeat.getType());
        return seat;
    }

    public static DTO_RP_SeatingChart toResponseDTO(SeatingChart seatingChart) {
        DTO_RP_SeatingChart dto = new DTO_RP_SeatingChart();
        dto.setId(seatingChart.getId());
        dto.setName(seatingChart.getSeatChartName());
        dto.setRow(seatingChart.getTotalRow());
        dto.setFloor(seatingChart.getTotalFloor());
        dto.setColumn(seatingChart.getTotalColumn());

        List<DTO_RP_Seat> seats = seatingChart.getSeats().stream()
                .map(seat -> {
                    DTO_RP_Seat seatDTO = new DTO_RP_Seat();
                    seatDTO.setId(seat.getId());
                    seatDTO.setName(seat.getSeatName());
                    seatDTO.setCode(seat.getSeatCode());
                    seatDTO.setType(seat.getSeatType());
                    seatDTO.setColumn(seat.getSeatColumn());
                    seatDTO.setFloor(seat.getSeatFloor());
                    seatDTO.setRow(seat.getSeatRow());
                    return seatDTO;
                }).collect(Collectors.toList());
        dto.setSeats(seats);
        return dto;
    }

    public static DTO_RP_SeatingChartName toSeatChartNameDTO(SeatingChart seatingChart) {
        DTO_RP_SeatingChartName dto = new DTO_RP_SeatingChartName();
        dto.setId(seatingChart.getId());
        dto.setName(seatingChart.getSeatChartName());
        return dto;
    }


//    public static DTO_RP_SeatingChart toResponseDTO(SeatingChart seatingChart) {
//        DTO_RP_SeatingChart responseDTO = new DTO_RP_SeatingChart();
//        responseDTO.setId(seatingChart.getId());
//        responseDTO.setSeatingChartName(seatingChart.getSeatingChartName());
//        responseDTO.setTotalFloors(seatingChart.getTotalFloors());
//        responseDTO.setTotalRows(seatingChart.getTotalRows());
//        responseDTO.setTotalColumns(seatingChart.getTotalColumns());
//
//        List<DTO_RP_Seat> seatResponseDTOS = seatingChart.getSeats().stream()
//                .map(seat -> {
//                    DTO_RP_Seat seatDTO = new DTO_RP_Seat();
//                    seatDTO.setId(seat.getId());
//                    seatDTO.setSeatCode(seat.getSeatCode());
//                    seatDTO.setSeatName(seat.getSeatName());
//                    seatDTO.setSeatStatus(seat.getSeatStatus());
//                    seatDTO.setRow(seat.getRow());
//                    seatDTO.setColumn(seat.getColumn());
//                    seatDTO.setFloor(seat.getFloor());
//                    return seatDTO;
//                })
//                .collect(Collectors.toList());
//
//        responseDTO.setSeats(seatResponseDTOS);
//        return responseDTO;
//    }
//    public static DTO_RP_SeatingChartName toResponseNameDTO(SeatingChart seatingChart) {
//        DTO_RP_SeatingChartName responseDTO = new DTO_RP_SeatingChartName();
//        responseDTO.setId(seatingChart.getId());
//        responseDTO.setSeatingChartName(seatingChart.getSeatingChartName());
//        return responseDTO;
//    }
}
