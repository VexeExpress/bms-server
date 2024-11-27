package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Trip.TripRequestDTO;
import com.bms.bms_server.entity.Trip;
import com.bms.bms_server.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trip")
@CrossOrigin(origins = "http://localhost:3000")
public class TripController {
    @Autowired
    TripService tripService;

    @PostMapping("/create")
    public ResponseEntity<Trip> createTrip(@RequestBody TripRequestDTO tripRequestDTO) {
        try {
            Trip newTrip = tripService.createTrip(tripRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTrip); // Trả về Trip mới và mã HTTP 201
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null); // Nếu có lỗi thì trả về mã HTTP 400
        }
    }
}
