package com.bms.bms_server.modules.ModuleSeat.controller;

import com.bms.bms_server.modules.ModuleSeat.dto.DTO_RQ_EditSeatChart;
import com.bms.bms_server.modules.ModuleSeat.dto.DTO_RP_SeatingChartName;
import com.bms.bms_server.modules.ModuleSeat.dto.DTO_RQ_SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.dto.DTO_RP_SeatingChart;
import com.bms.bms_server.modules.ModuleSeat.service.SeatingChartService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/create")
    public ResponseEntity<DTO_RP_SeatingChart> createSeatingChart(@RequestBody DTO_RQ_SeatingChart seatingChart) {
        try {
            DTO_RP_SeatingChart savedChart = seatingChartService.createSeatingChart(seatingChart);
            return new ResponseEntity<>(savedChart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/list-chart/{companyId}")
    public ResponseEntity<List<DTO_RP_SeatingChart>> getListSeatChartByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_SeatingChart> seatingCharts = seatingChartService.getListSeatChartByCompanyId(companyId);
            return new ResponseEntity<>(seatingCharts, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/{seatChartId}")
    public ResponseEntity<Void> deleteSeatingChart(@PathVariable Long seatChartId) {
        try {
            seatingChartService.deleteSeatingChart(seatChartId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<DTO_RP_SeatingChart> updateSeatingChart(
            @PathVariable Long id,
            @RequestBody DTO_RQ_EditSeatChart updatedData) {
            System.out.println("Received data: " + updatedData);
        try {
            DTO_RP_SeatingChart updatedChart = seatingChartService.updateSeatingChart(id, updatedData);
            return new ResponseEntity<>(updatedChart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/list-seating-chart-name/{companyId}")
    public ResponseEntity<List<DTO_RP_SeatingChartName>> getListSeatingChartNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_SeatingChartName> response = seatingChartService.getListSeatingChartNameByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
}
