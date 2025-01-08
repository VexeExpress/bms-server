package com.bms.bms_server.modules.ModuleSeat.controller;

import com.bms.bms_server.modules.ModuleSeat.dto.*;
import com.bms.bms_server.modules.ModuleSeat.service.SeatingChartService;
import com.bms.bms_server.utils.ApiResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seat")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SeatingChartController {
    @Autowired
    SeatingChartService seatingChartService;

    // PB.06_US.01: Add new seating chart
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_SeatingChart> addNewSeatingChart(@RequestBody DTO_RQ_CreateSeatChart dto) {
        var result = seatingChartService.addNewSeatingChart(dto);
        return ApiResponse.<DTO_RP_SeatingChart>builder()
                .code(1000)
                .message("Tạo sơ đồ ghế thành công")
                .result(result)
                .build();
    }

    // PB.06_US.02: Update seating chart
    @PutMapping("/update/{seatChartId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_SeatingChart> updateSeatingChart(@PathVariable Long seatChartId, @RequestBody DTO_RQ_EditSeatChart dto) {
        var result = seatingChartService.updateSeatingChart(seatChartId, dto);
        return ApiResponse.<DTO_RP_SeatingChart>builder()
                .code(1000)
                .message("Cập nhật thông tin sơ đồ thành công")
                .result(result)
                .build();
    }

    // PB.06_US.03: Remove seating chart
    @DeleteMapping("/delete/{seatChartId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> removeSeatingChart(@PathVariable Long seatChartId) {
        seatingChartService.removeSeatingChart(seatChartId);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xoá sơ đồ thành công")
                .build();
    }

    // PB.06_US.04: Filter/Get list seating chart
    @GetMapping("/list-chart/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_SeatingChart>> getListSeatChartByCompanyId(@PathVariable Long companyId) {
        var result = seatingChartService.getListSeatChartByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_SeatingChart>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

//    @PostMapping("/create")
//    public ResponseEntity<DTO_RP_SeatingChart> createSeatingChart(@RequestBody DTO_RQ_CreateSeatChart seatingChart) {
//        try {
//            DTO_RP_SeatingChart savedChart = seatingChartService.createSeatingChart(seatingChart);
//            return new ResponseEntity<>(savedChart, HttpStatus.CREATED);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @GetMapping("/list-chart/{companyId}")
//    public ResponseEntity<List<DTO_RP_SeatingChart>> getListSeatChartByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_SeatingChart> seatingCharts = seatingChartService.getListSeatChartByCompanyId(companyId);
//            return new ResponseEntity<>(seatingCharts, HttpStatus.OK);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
//        }
//    }
//    @DeleteMapping("/delete/{seatChartId}")
//    public ResponseEntity<Void> deleteSeatingChart(@PathVariable Long seatChartId) {
//        try {
//            seatingChartService.deleteSeatingChart(seatChartId);
//            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//    @PutMapping("/update/{id}")
//    public ResponseEntity<DTO_RP_SeatingChart> updateSeatingChart(
//            @PathVariable Long id,
//            @RequestBody DTO_RQ_EditSeatChart updatedData) {
//            System.out.println("Received data: " + updatedData);
//        try {
//            DTO_RP_SeatingChart updatedChart = seatingChartService.updateSeatingChart(id, updatedData);
//            return new ResponseEntity<>(updatedChart, HttpStatus.OK);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        } catch (Exception e) {
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//    @GetMapping("/list-seating-chart-name/{companyId}")
//    public ResponseEntity<List<DTO_RP_SeatingChartName>> getListSeatingChartNameByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_SeatingChartName> response = seatingChartService.getListSeatingChartNameByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
}
