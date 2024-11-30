package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Office.OfficeNameResponseDTO;
import com.bms.bms_server.dto.Office.OfficeRequestDTO;
import com.bms.bms_server.dto.Office.OfficeResponseDTO;
import com.bms.bms_server.service.OfficeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/office")
@CrossOrigin(origins = "http://localhost:3000")
public class OfficeController {
    @Autowired
    OfficeService officeService;

    @PostMapping("/create")
    public ResponseEntity<OfficeResponseDTO> create(@RequestBody OfficeRequestDTO dto) {
        try {
            OfficeResponseDTO responseDto = officeService.createOffice(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto); // 201
        } catch (IllegalArgumentException e) {
            if (e.getMessage().contains("required")) {
                return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).build(); // 422: Du lieu dau vao loi
            } else if (e.getMessage().contains("already exists")) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Van phong da ton tai trong cong ty
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Du lieu vao loi
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Cong ty khong ton tai
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @GetMapping("/list-office/{companyId}")
    public ResponseEntity<List<OfficeResponseDTO>> getListOfficeByCompanyId(@PathVariable Long companyId) {
        try {
            List<OfficeResponseDTO> officeResponse = officeService.getListOfficeByCompanyId(companyId);
            return ResponseEntity.ok(officeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @PutMapping("/update/{officeId}")
    public ResponseEntity<OfficeResponseDTO> updateOffice(
            @PathVariable Long officeId,
            @RequestBody OfficeRequestDTO updatedData) {
        try {
            OfficeResponseDTO updatedOffice = officeService.updateOffice(officeId, updatedData);
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
    public ResponseEntity<List<OfficeNameResponseDTO>> getListOfficeNameByCompanyId(@PathVariable Long companyId) {
        try {
            List<OfficeNameResponseDTO> officeResponse = officeService.getListOfficeNameByCompanyId(companyId);
            return ResponseEntity.ok(officeResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

}
