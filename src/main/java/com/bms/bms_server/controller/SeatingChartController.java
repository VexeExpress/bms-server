package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Route.RouteNameResponseDTO;
import com.bms.bms_server.dto.SeatingChart.EditSeatChartDTO;
import com.bms.bms_server.dto.SeatingChart.SeatingChartNameDTO;
import com.bms.bms_server.dto.SeatingChart.SeatingChartRequestDTO;
import com.bms.bms_server.dto.SeatingChart.SeatingChartResponseDTO;
import com.bms.bms_server.entity.SeatingChart;
import com.bms.bms_server.service.SeatingChartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seating-chart")
@CrossOrigin(origins = "http://localhost:3000")
public class SeatingChartController {
    @Autowired
    SeatingChartService seatingChartService;

    @PostMapping("/create")
    public ResponseEntity<SeatingChartResponseDTO> createSeatingChart(@RequestBody SeatingChartRequestDTO seatingChart) {
        try {
            SeatingChartResponseDTO savedChart = seatingChartService.createSeatingChart(seatingChart);
            return new ResponseEntity<>(savedChart, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @GetMapping("/list-chart/{companyId}")
    public ResponseEntity<List<SeatingChartResponseDTO>> getListSeatChartByCompanyId(@PathVariable Long companyId) {
        try {
            List<SeatingChartResponseDTO> seatingCharts = seatingChartService.getListSeatChartByCompanyId(companyId);
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
    public ResponseEntity<SeatingChartResponseDTO> updateSeatingChart(
            @PathVariable Long id,
            @RequestBody EditSeatChartDTO updatedData) {
            System.out.println("Received data: " + updatedData);
        try {
            SeatingChartResponseDTO updatedChart = seatingChartService.updateSeatingChart(id, updatedData);
            return new ResponseEntity<>(updatedChart, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/list-seating-chart-name/{companyId}")
    public ResponseEntity<List<SeatingChartNameDTO>> getListSeatingChartNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<SeatingChartNameDTO> response = seatingChartService.getListSeatingChartNameByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
}
