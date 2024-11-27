package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.SeatingChart.SeatRequestDTO;
import com.bms.bms_server.dto.SeatingChart.SeatResponseDTO;
import com.bms.bms_server.dto.SeatingChart.SeatingChartRequestDTO;
import com.bms.bms_server.dto.SeatingChart.SeatingChartResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Seat;
import com.bms.bms_server.entity.SeatingChart;

import java.util.List;
import java.util.stream.Collectors;

public class SeatChartMapper {
    public static SeatingChart toEntity(SeatingChartRequestDTO dto, Company company) {
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

    private static Seat mapSeatToEntity(SeatRequestDTO seatRequestDTO) {
        Seat seat = new Seat();
        seat.setFloor(seatRequestDTO.getFloor());
        seat.setRow(seatRequestDTO.getRow());
        seat.setColumn(seatRequestDTO.getColumn());
        seat.setSeatCode(seatRequestDTO.getSeatCode());
        seat.setSeatName(seatRequestDTO.getSeatName());
        seat.setSeatStatus(seatRequestDTO.getSeatStatus());
        return seat;
    }
    public static SeatingChartResponseDTO toResponseDTO(SeatingChart seatingChart) {
        SeatingChartResponseDTO responseDTO = new SeatingChartResponseDTO();
        responseDTO.setId(seatingChart.getId());
        responseDTO.setSeatingChartName(seatingChart.getSeatingChartName());
        responseDTO.setTotalFloors(seatingChart.getTotalFloors());
        responseDTO.setTotalRows(seatingChart.getTotalRows());
        responseDTO.setTotalColumns(seatingChart.getTotalColumns());

        List<SeatResponseDTO> seatResponseDTOS = seatingChart.getSeats().stream()
                .map(seat -> {
                    SeatResponseDTO seatDTO = new SeatResponseDTO();
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
}
