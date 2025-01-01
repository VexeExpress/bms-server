package com.bms.bms_server.modules.ModuleVehicle.controller;

import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Employee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_EditEmployee;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_LicensePlate;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RQ_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.dto.DTO_RP_Vehicle;
import com.bms.bms_server.modules.ModuleVehicle.service.VehicleService;
import com.bms.bms_server.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
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
@RequestMapping("/api/vehicle")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class VehicleController {
    @Autowired
    VehicleService vehicleService;

    // PB.03_US.01: Add new vehicle
    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Vehicle> addNewVehicle(@RequestBody DTO_RQ_Vehicle dto) {
        var result = vehicleService.createVehicle(dto);
        return ApiResponse.<DTO_RP_Vehicle>builder()
                .code(1000)
                .message("Tạo phương tiện mới thành công")
                .result(result)
                .build();
    }

    // PB.03_US.02: Filter/Get Vehicle List
    @GetMapping("/list-vehicle/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<List<DTO_RP_Vehicle>> getListVehicleByCompanyId(@PathVariable Long companyId) {
        var result = vehicleService.getListVehicleByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_Vehicle>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }

    // PB.03_US.03: Update vehicle information
    @PutMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<DTO_RP_Vehicle> updateVehicle(@PathVariable Long id, @RequestBody DTO_RQ_Vehicle dto) {
        var result = vehicleService.updateVehicle(id, dto);
        return ApiResponse.<DTO_RP_Vehicle>builder()
                .code(1000)
                .message("Cập nhật thông tin phương tiện thành công")
                .result(result)
                .build();
    }

    // PB.03_US.04: Remove vehicle
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN')")
    ApiResponse<Void> deleteVehicle(@PathVariable Long id) {
        vehicleService.deleteVehicleById(id);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xoá phương tiện thành công")
                .build();
    }

    // PB.03_US.05: Filter/Get list of license plates
    @GetMapping("/list-license-plate/{companyId}")
    @PreAuthorize("hasRole('ADMIN_APP') or hasRole('ADMIN') or hasRole('EMPLOYEE')")
    ApiResponse<List<DTO_RP_LicensePlate>> getLicensePlateVehicleByCompanyId(@PathVariable Long companyId) {
        var result = vehicleService.getLicensePlateVehicleByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_LicensePlate>>builder()
                .code(1000)
                .message("Tải dữ liệu thành công")
                .result(result)
                .build();
    }



//    @GetMapping("/list-vehicle/{companyId}")
//    public ResponseEntity<List<DTO_RP_Vehicle>> getListVehicleByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_Vehicle> response = vehicleService.getListVehicleByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
    // UC_VE_01: Thêm phương tiện
//    @PostMapping("/create")
//    public ResponseEntity<DTO_RP_Vehicle> create(@RequestBody DTO_RQ_Vehicle dto) {
//        System.out.println(dto);
//        try {
//            DTO_RP_Vehicle responseDto = vehicleService.createVehicle(dto);
//            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201
//        } catch (IllegalArgumentException e) {
//            if (e.getMessage().contains("required")) {
//                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422: Du lieu dau vao loi
//            } else if (e.getMessage().contains("already exists")) {
//                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Phuong tien da ton tai trong cong ty
//            }
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Du lieu vao loi
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Cong ty khong ton tai
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }



//    @PutMapping("/update/{vehicleId}")
//    public ResponseEntity<DTO_RP_Vehicle> updateVehicle(
//            @PathVariable Long vehicleId,
//            @RequestBody DTO_RQ_Vehicle updatedData) {
//        try {
//            System.out.println(updatedData);
//            DTO_RP_Vehicle updated = vehicleService.updateVehicle(vehicleId, updatedData);
//            return ResponseEntity.ok(updated); // 200: Thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy phuong tien
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }

//    @DeleteMapping("/delete/{vehicleId}")
//    public ResponseEntity<Void> deleteVehicleById(@PathVariable Long vehicleId) {
//        try {
//            vehicleService.deleteVehicleById(vehicleId);
//            return ResponseEntity.noContent().build(); // 204: Xóa thành công
//        } catch (EntityNotFoundException e) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy phuong tien
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
//        }
//    }
//    @GetMapping("/list-license-plate/{companyId}")
//    public ResponseEntity<List<DTO_RP_LicensePlate>> getLicensePlateVehicleByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_LicensePlate> response = vehicleService.getLicensePlateVehicleByCompanyId(companyId);
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
}
