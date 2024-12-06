package com.bms.bms_server.modules.ModuleVehicle.controller;

import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_LicensePlate;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RQ_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.service.VehicleService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "http://localhost:3000")
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    // UC_VE_01: Thêm phương tiện
    @PostMapping("/create")
    public ResponseEntity<DTO_RP_Vehicle> create(@RequestBody DTO_RQ_Vehicle dto) {
        System.out.println(dto);
        try {
            DTO_RP_Vehicle responseDto = vehicleService.createVehicle(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("required")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422: Du lieu dau vao loi
            } else if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Phuong tien da ton tai trong cong ty
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Du lieu vao loi
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Cong ty khong ton tai
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }

    @GetMapping("/list-vehicle/{companyId}")
    public ResponseEntity<List<DTO_RP_Vehicle>> getListVehicleByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_Vehicle> response = vehicleService.getListVehicleByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }

    @PutMapping("/update/{vehicleId}")
    public ResponseEntity<DTO_RP_Vehicle> updateVehicle(
            @PathVariable Long vehicleId,
            @RequestBody DTO_RQ_Vehicle updatedData) {
        try {
            System.out.println(updatedData);
            DTO_RP_Vehicle updated = vehicleService.updateVehicle(vehicleId, updatedData);
            return ResponseEntity.ok(updated); // 200: Thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy phuong tien
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    @DeleteMapping("/delete/{vehicleId}")
    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long vehicleId) {
        try {
            vehicleService.deleteVehicleById(vehicleId);
            return ResponseEntity.noContent().build(); // 204: Xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy phuong tien
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    @GetMapping("/list-license-plate/{companyId}")
    public ResponseEntity<List<DTO_RP_LicensePlate>> getLicensePlateVehicleByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_LicensePlate> response = vehicleService.getLicensePlateVehicleByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
}
