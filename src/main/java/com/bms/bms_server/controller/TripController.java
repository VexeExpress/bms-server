package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Trip.TripInfoResponseDTO;
import com.bms.bms_server.dto.Trip.TripRequestDTO;
import com.bms.bms_server.entity.Trip;
import com.bms.bms_server.service.TripService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<TripInfoResponseDTO> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        try {
            TripInfoResponseDTO newTrip = tripService.createTrip(tripRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTrip); // Trả về Trip mới và mã HTTP 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Nếu có lỗi thì trả về mã HTTP 400
        }
    }
    @GetMapping("/search-trip")
    public ResponseEntity<List<TripInfoResponseDTO>> getTripsByCompanyIdAndRouteIdAndDate(
            @RequestParam Long companyId,
            @RequestParam Long routeId,
            @RequestParam LocalDate dateDeparture) {
        try {
            List<TripInfoResponseDTO> trips = tripService.getTripsByCompanyIdAndRouteIdAndDate(companyId, routeId, dateDeparture);
            System.out.println(trips);
            return ResponseEntity.status(HttpStatus.OK).body(trips);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    @DeleteMapping("/delete/{tripId}")
    public ResponseEntity<Void> deleteTripById(@PathVariable Long tripId) {
        try {
            tripService.deleteTripById(tripId);
            return ResponseEntity.noContent().build();
        }  catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
