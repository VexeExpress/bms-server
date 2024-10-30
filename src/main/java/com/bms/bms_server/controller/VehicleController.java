package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Vehicle.request.VehicleRequestDTO;
import com.bms.bms_server.dto.Vehicle.response.VehicleResponseDTO;
import com.bms.bms_server.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vehicle")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    // UC_VE_01: Thêm phương tiện
    @PostMapping("/create")
    public ResponseEntity<VehicleResponseDTO> createVehicle(@RequestBody VehicleRequestDTO dto) {
        if (dto.getLicensePlate() == null || dto.getLicensePlate().isEmpty() || dto.getType() == null || dto.getCompanyId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (vehicleService.licensePlateExists(dto.getLicensePlate(), dto.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Phương tiện đã tồn tại trong công ty này
        }
        try {
            VehicleResponseDTO createdVehicle = vehicleService.createVehicle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdVehicle); // 201: Tạo thành công
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400: Dữ liệu vào không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
}
