package com.bms.bms_server.modules.ModuleSeat.service;

import com.bms.bms_server.exception.AppException;
import com.bms.bms_server.exception.ErrorCode;
import com.bms.bms_server.modules.ModuleCompany.entity.Company;
import com.bms.bms_server.modules.ModuleSeat.dto.*;
import com.bms.bms_server.modules.ModuleSeat.entity.Seat;
import com.bms.bms_server.modules.ModuleSeat.entity.SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.mapper.SeatChartMapper;
import com.bms.bms_server.modules.ModuleCompany.repository.CompanyRepository;
import com.bms.bms_server.modules.ModuleSeat.repositoty.SeatRepository;
import com.bms.bms_server.modules.ModuleSeat.repositoty.SeatingChartRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class SeatingChartService {
    @Autowired
    SeatingChartRepository seatingChartRepository;
    @Autowired
    CompanyRepository companyRepository;
    @Autowired
    SeatRepository seatRepository;

    // PB.06_US.01: Add new seating chart
    public DTO_RP_SeatingChart addNewSeatingChart(DTO_RQ_CreateSeatChart dto) {
        if (dto.getCompanyId() == null) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_NAME_SEAT_CHART);
        }
        Company company = companyRepository.findById(dto.getCompanyId())
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));
        boolean existsSeatChartName = seatingChartRepository.existsByCompanyAndSeatChartName(company, dto.getName());
        if (existsSeatChartName) {
            throw new AppException(ErrorCode.SEAT_CHART_ALREADY_EXISTED);
        }
        SeatingChart seatingChart = SeatChartMapper.toEntity(dto, company);
        SeatingChart savedSeatingChart = seatingChartRepository.save(seatingChart);
        return SeatChartMapper.toResponseDTO(savedSeatingChart);
    }

    // PB.06_US.04: Filter/Get list seating chart
    public List<DTO_RP_SeatingChart> getListSeatChartByCompanyId(Long companyId) {
        if (companyId == null || companyId <= 0) {
            throw new AppException(ErrorCode.INVALID_COMPANY_ID);
        }
        companyRepository.findById(companyId)
                .orElseThrow(() -> new AppException(ErrorCode.COMPANY_NOT_EXIST));

        List<SeatingChart> seatingCharts = seatingChartRepository.findByCompanyId(companyId);
        return seatingCharts.stream()
                .map(SeatChartMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    // PB.06_US.02: Update seating chart
    @Transactional
    public DTO_RP_SeatingChart updateSeatingChart(Long seatChartId, DTO_RQ_EditSeatChart dto) {
        if (dto.getName() == null || dto.getName().isEmpty()) {
            throw new AppException(ErrorCode.INVALID_NAME_SEAT_CHART);
        }
        SeatingChart existingChart = seatingChartRepository.findById(seatChartId)
                        .orElseThrow(() -> new AppException(ErrorCode.SEAT_CHART_NOT_FOUND));
        existingChart.setSeatChartName(dto.getName());
        existingChart.setTotalFloor(dto.getFloor());
        existingChart.setTotalRow(dto.getRow());
        existingChart.setTotalColumn(dto.getColumn());
        updateSeats(existingChart, dto.getSeats(), dto.getFloor(), dto.getRow(), dto.getColumn());
        seatingChartRepository.save(existingChart);
        return SeatChartMapper.toResponseDTO(existingChart);
    }

    private void updateSeats(SeatingChart existingChart, List<DTO_RQ_EditSeat> seats, Integer floor, Integer row, Integer column) {
        List<Seat> seatList = existingChart.getSeats();
        Map<String, Seat> stringSeatMap = seatList.stream().collect(Collectors.toMap(Seat:: getSeatCode, Function.identity()));

        Set<String> updateSeatCode = seats.stream().map(DTO_RQ_EditSeat::getCode).collect(Collectors.toSet());

        for (DTO_RQ_EditSeat editSeat: seats) {
            Seat seat = stringSeatMap.get(editSeat.getCode());
            if (seat != null) {
                seat.setSeatName(editSeat.getName());
                seat.setSeatType(editSeat.getType());
                seat.setSeatRow(editSeat.getRow());
                seat.setSeatFloor(editSeat.getFloor());
                seat.setSeatColumn(editSeat.getColumn());
            } else {
                Seat newSeat = new Seat();
                newSeat.setSeatCode(editSeat.getCode());
                newSeat.setSeatName(editSeat.getName());
                newSeat.setSeatColumn(editSeat.getColumn());
                newSeat.setSeatRow(editSeat.getRow());
                newSeat.setSeatFloor(editSeat.getFloor());
                newSeat.setSeatType(editSeat.getType());
                existingChart.getSeats().add(newSeat);
            }
        }
        System.out.println("Floor: " + (floor));
        System.out.println("Row: " + (row));
        System.out.println("Column: " + (column));

        List<Seat> seatsToDelete = seatList.stream()
                .filter(seat ->
                        !updateSeatCode.contains(seat.getSeatCode()) || seat.getSeatColumn() > column || seat.getSeatFloor() > floor || seat.getSeatRow() > row)
                .toList();

        if (seatsToDelete.isEmpty()) {
            System.out.println("Không có ghế nào cần xóa.");
        } else {
            System.out.println("Ghế cần xoá:");
            for (Seat seat: seatsToDelete) {
                System.out.println("Seat code: " + seat.getSeatCode());
            }
            existingChart.getSeats().removeAll(seatsToDelete);
        }
    }

    public void removeSeatingChart(Long seatChartId) {
        Optional<SeatingChart> seatingChartOptional = seatingChartRepository.findById(seatChartId);
        if (seatingChartOptional.isEmpty()) {
            throw new AppException(ErrorCode.SEAT_CHART_NOT_FOUND);
        }
        seatingChartRepository.deleteById(seatChartId);
    }


//
//    public List<DTO_RP_SeatingChartName> getListSeatingChartNameByCompanyId(Long companyId) {
//        List<SeatingChart> seatingCharts = seatingChartRepository.findByCompanyId(companyId);
//        return seatingCharts.stream()
//                .map(SeatChartMapper::toResponseNameDTO)
//                .collect(Collectors.toList());
//    }
//
//    public DTO_RP_SeatingChart addNewSeatingChart(DTO_RQ_CreateSeatChart dto) {
//        System.out.println(dto);
//        return null;
//    }
}
