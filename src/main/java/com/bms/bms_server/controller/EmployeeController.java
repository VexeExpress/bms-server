package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Employee.request.EmployeeRequestDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeResponseDTO;
import com.bms.bms_server.entity.Employee;
import com.bms.bms_server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    // UC_EM_01: Thêm nhân viên mới
    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto){
        if (dto.getUsername() == null || dto.getUsername().isEmpty() ||
                dto.getFullName() == null || dto.getFullName().isEmpty() ||
                dto.getPassword() == null || dto.getPassword().isEmpty() ||
                dto.getRole() == null || dto.getCompanyId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (employeeService.usernameExists(dto.getUsername(), dto.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Username đã tồn tại trong công ty này
        }
        try {
            EmployeeResponseDTO createdEmployee = employeeService.createEmployee(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee); // 201: Tạo thành công
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400: Dữ liệu vào không hợp lệ
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    // UC_EM_02: Xóa nhân viên
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteEmployee (@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: ID không hợp lệ
        }
        try {
            if (!employeeService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: ID không tồn tại
            }
            employeeService.deleteEmployeeById(id);
            return ResponseEntity.noContent().build(); // 204: Xóa thành công
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    // UC_EM_03: Cập nhật thông tin nhân viên
    @PutMapping("/update/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee (@PathVariable Long id, @RequestBody EmployeeRequestDTO dto) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (dto.getUsername() == null || dto.getUsername().isEmpty() ||
                dto.getFullName() == null || dto.getFullName().isEmpty() ||
                dto.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (!employeeService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: User không tồn tại
        }
        if (employeeService.isUsernameTakenInCompanyByAnotherEmployee(id, dto.getUsername(), dto.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409: Tên người dùng đã tồn tại trong công ty
        }
        try {
            EmployeeResponseDTO updatedEmployee = employeeService.updateEmployee(id, dto);
            return ResponseEntity.ok(updatedEmployee); // 200: Cap nhat thanh cong
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400: Du lieu vao khong hop le
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }

    // UC_EM_04: Khóa tài khoản nhân viên
    @PostMapping("/lock/{id}")
    public ResponseEntity<Void> lockAccountEmployee (@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: ID không hợp lệ
        }
        try {
            if (!employeeService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: ID không tồn tại
            }
            employeeService.lockAccountEmployee(id);
            return ResponseEntity.noContent().build(); // 204: Khóa thành công
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404: ID không tồn tại
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    // UC_EM_05: Đặt lại mật khẩu mặc định “12345678”
    @PostMapping("/change-pass/{id}")
    public ResponseEntity<Void> changePassAccountEmployee (@PathVariable Long id) {
        if (id == null || id <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: ID không hợp lệ
        }
        try {
            if (!employeeService.existsById(id)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: ID không tồn tại
            }
            employeeService.changePassAccountEmployee(id);
            return ResponseEntity.noContent().build(); // 204: Thay đổi mật khẩu thành công
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build(); // 404: ID không tồn tại
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    // UC_EM_06: Tìm kiếm nhân viên theo “tên” - Ở FE xử lý được thì BE không cần làm

    // UC_EM_07: Lọc nhân viên theo “vai trò” - Ở FE xử lý được thì BE không cần làm

    // UC_EM_08: Lấy danh sách nhân viên dựa vào ID của công ty
    @GetMapping("/list-employee/{companyId}")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeesByCompanyId(@PathVariable Long companyId) {
        if (companyId == null || companyId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: ID công ty không hợp lệ
        }
        try {
            List<EmployeeResponseDTO> employees = employeeService.getEmployeesByCompanyId(companyId);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không có nhân viên nào thuộc công ty
            }
            return ResponseEntity.ok(employees); // 200: Trả về danh sách nhân viên
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }













}
