package com.bms.bms_server.service;

import com.bms.bms_server.dto.SeatingChart.*;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Seat;
import com.bms.bms_server.entity.SeatingChart;
import com.bms.bms_server.mapper.SeatChartMapper;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.SeatRepository;
import com.bms.bms_server.repository.SeatingChartRepository;
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

    public SeatingChartResponseDTO createSeatingChart(SeatingChartRequestDTO dto) {
        try {
            // Tìm công ty từ companyId
            Company company = companyRepository.findById(dto.getCompanyId())
                    .orElseThrow(() -> new RuntimeException("Company not found"));

            // Chuyển DTO thành Entity bằng Mapper
            SeatingChart seatingChart = SeatChartMapper.toEntity(dto, company);

            // Liên kết từng seat với seatingChart
//            seatingChart.getSeats().forEach(seat -> seat.setSeatingChart(seatingChart));

            // Lưu seatingChart vào database (các seat cũng được lưu nhờ Cascade)
            SeatingChart savedSeatingChart = seatingChartRepository.save(seatingChart);

            // Chuyển đối tượng seatingChart đã lưu thành SeatingChartResponseDTO
            return SeatChartMapper.toResponseDTO(savedSeatingChart);
        } catch (Exception e) {
            throw new RuntimeException("Error creating seating chart: " + e.getMessage());
        }
    }

    public List<SeatingChartResponseDTO> getListSeatChartByCompanyId(Long companyId) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found"));

        List<SeatingChart> seatingCharts = seatingChartRepository.findByCompany(company);

        return seatingCharts.stream()
                .map(SeatChartMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public void deleteSeatingChart(Long seatChartId) {
        SeatingChart seatingChart = seatingChartRepository.findById(seatChartId)
                .orElseThrow(() -> new RuntimeException("Seating chart not found"));
        seatingChartRepository.delete(seatingChart);
    }

    @Transactional
    public SeatingChartResponseDTO updateSeatingChart(Long id, EditSeatChartDTO updatedData) {
        SeatingChart existingChart = seatingChartRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Seating Chart not found with id: " + id));

        existingChart.setSeatingChartName(updatedData.getSeatingChartName());
        existingChart.setTotalFloors(updatedData.getTotalFloors());
        existingChart.setTotalRows(updatedData.getTotalRows());
        existingChart.setTotalColumns(updatedData.getTotalColumns());

        updateSeats(existingChart, updatedData.getSeats(), updatedData.getTotalFloors(), updatedData.getTotalRows(), updatedData.getTotalColumns());
        seatingChartRepository.save(existingChart);
        return SeatChartMapper.toResponseDTO(existingChart);
    }



    private void updateSeats(SeatingChart seatingChart, List<EditSeatDTO> updatedSeats, int totalFloors, int totalRows, int totalColumns) {
        List<Seat> existingSeats = seatingChart.getSeats();
        Map<String, Seat> seatCodeMap = existingSeats.stream()
                .collect(Collectors.toMap(Seat::getSeatCode, Function.identity()));

        // Tạo một danh sách các seatCode trong danh sách cập nhật
        Set<String> updatedSeatCodes = updatedSeats.stream()
                .map(EditSeatDTO::getSeatCode)
                .collect(Collectors.toSet());

        // Lặp qua danh sách ghế mới để cập nhật hoặc thêm mới
        for (EditSeatDTO updatedSeat : updatedSeats) {
            Seat seat = seatCodeMap.get(updatedSeat.getSeatCode());

            if (seat != null) {
                // Cập nhật ghế đã tồn tại
                seat.setSeatName(updatedSeat.getSeatName());
                seat.setSeatStatus(updatedSeat.getSeatStatus());
                seat.setFloor(updatedSeat.getFloor());
                seat.setRow(updatedSeat.getRow());
                seat.setColumn(updatedSeat.getColumn());
            } else {
                // Thêm ghế mới nếu không tồn tại
                Seat newSeat = new Seat();
                newSeat.setSeatCode(updatedSeat.getSeatCode());
                newSeat.setSeatName(updatedSeat.getSeatName());
                newSeat.setSeatStatus(updatedSeat.getSeatStatus());
                newSeat.setFloor(updatedSeat.getFloor());
                newSeat.setRow(updatedSeat.getRow());
                newSeat.setColumn(updatedSeat.getColumn());
//                newSeat.setSeatingChart(seatingChart);
                seatingChart.getSeats().add(newSeat);
            }
        }

        // Xóa ghế cũ không có trong danh sách ghế mới và không thuộc vào tầng, hàng, cột hợp lệ
        System.out.println("Floor: " + (totalFloors - 1));
        System.out.println("Row: " + (totalRows - 1));
        System.out.println("Column: " + (totalColumns - 1));


        // Lọc ra các ghế cần xóa
        List<Seat> seatsToDelete = existingSeats.stream()
                .filter(seat ->
                        !updatedSeatCodes.contains(seat.getSeatCode()) || // Không có trong danh sách cập nhật
                                seat.getFloor() >= totalFloors ||                // Nằm ngoài tầng hợp lệ
                                seat.getRow() >= totalRows ||                    // Nằm ngoài hàng hợp lệ
                                seat.getColumn() >= totalColumns)                // Nằm ngoài cột hợp lệ
                .collect(Collectors.toList());
        // In ra danh sách ghế sẽ bị xóa
        if (seatsToDelete.isEmpty()) {
            System.out.println("Không có ghế nào cần xóa.");
        } else {
            System.out.println("Ghế cần xoá:");
            for (Seat seat : seatsToDelete) {
                System.out.println("Seat Code: " + seat.getSeatCode() +
                        ", Floor: " + seat.getFloor() +
                        ", Row: " + seat.getRow() +
                        ", ID: " + seat.getId() +
                        ", Name: " + seat.getSeatName() +
                        ", Column: " + seat.getColumn());
            }
            // Xóa các ghế trong danh sách seatsToDelete khỏi danh sách existingSeats
            seatingChart.getSeats().removeAll(seatsToDelete);
        }


    }


    public List<SeatingChartNameDTO> getListSeatingChartNameByCompanyId(Long companyId) {
        List<SeatingChart> seatingCharts = seatingChartRepository.findByCompanyId(companyId);
        return seatingCharts.stream()
                .map(SeatChartMapper::toResponseNameDTO)
                .collect(Collectors.toList());
    }
}
