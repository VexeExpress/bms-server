package com.bms.bms_server.modules.ModuleOffice.controller;

import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_OfficeName;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RQ_Office;
import com.bms.bms_server.modules.ModuleOffice.dto.DTO_RP_Office;
import com.bms.bms_server.modules.ModuleOffice.service.OfficeService;
import com.bms.bms_server.utils.ApiResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/office")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class OfficeController {
    @Autowired
    OfficeService officeService;

    @PostMapping("/create")
    public ApiResponse<DTO_RP_Office> create(@RequestBody DTO_RQ_Office dto) {
        ApiResponse<DTO_RP_Office> apiResponse = new ApiResponse<>();
        apiResponse.setResult(officeService.createOffice(dto));
        return apiResponse;
    }
//    @GetMapping("/list-office/{companyId}")
//    public ResponseEntity<List<DTO_RP_Office>> getListOfficeByCompanyId(@PathVariable Long companyId) {
//        try {
//            List<DTO_RP_Office> officeResponse = officeService.getListOfficeByCompanyId(companyId);
//            return ResponseEntity.ok(officeResponse);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
//        }
//    }
    @GetMapping("/list-office/{companyId}")
    ApiResponse<List<DTO_RP_Office>> getListOfficeByCompanyId(@PathVariable Long companyId) {
        List<DTO_RP_Office> offices = officeService.getListOfficeByCompanyId(companyId);
        return ApiResponse.<List<DTO_RP_Office>>builder()
                .code(1000)
                .message("Lấy danh sách văn phòng thành công")
                .result(offices)
                .build();
    }
    @PutMapping("/update/{officeId}")
    public ResponseEntity<DTO_RP_Office> updateOffice(
            @PathVariable Long officeId,
            @RequestBody DTO_RQ_Office updatedData) {
        try {
            DTO_RP_Office updatedOffice = officeService.updateOffice(officeId, updatedData);
            return ResponseEntity.ok(updatedOffice); // 200: Thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy văn phòng
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Yêu cầu không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    @DeleteMapping("/delete/{officeId}")
    public ResponseEntity<Void> deleteOffice(@PathVariable Long officeId) {
        try {
            officeService.deleteOfficeById(officeId);
            return ResponseEntity.noContent().build(); // 204: Xóa thành công
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy văn phòng
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    @GetMapping("/list-office-name/{companyId}")
    public ResponseEntity<List<DTO_RP_OfficeName>> getListOfficeNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_OfficeName> officeResponse = officeService.getListOfficeNameByCompanyId(companyId);
            return ResponseEntity.ok(officeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

}
