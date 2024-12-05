package com.bms.bms_server.modules.ModuleEmployee.controller;

import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_CreateEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_EditEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Assistant;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Driver;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Employee;
import com.bms.bms_server.modules.ModuleEmployee.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/api/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    // VIN-10: Add New Employee
    @PostMapping("/create")
    public ResponseEntity<Object> addNewEmployee(@RequestBody DTO_RQ_CreateEmployee dto) {
        // Kiểm tra các trường dữ liệu bắt buộc
        if (dto.getUsername() == null || dto.getUsername().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username không được để trống.");
        }
        if (dto.getFullName() == null || dto.getFullName().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Họ và tên không được để trống.");
        }
        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Mật khẩu không được để trống.");
        }
        if (dto.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Vai trò không được để trống.");
        }
        if (dto.getCompanyId() == null ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Mã công ty trống.");
        }
        try {
            // Kiểm tra xem username đã tồn tại chưa
            if (employeeService.usernameExists(dto.getUsername(), dto.getCompanyId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Username đã tồn tại trong công ty này.");
            }
            // Tiến hành tạo nhân viên
            employeeService.createEmployee(dto);
            return ResponseEntity.status(HttpStatus.CREATED).build(); // 201: Thành công

        } catch (IllegalArgumentException e) {
            // Lỗi xác thực dữ liệu
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }  catch (Exception e) {
            // Lỗi hệ thống không xác định
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(e.getMessage());
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
    public ResponseEntity<DTO_RP_Employee> updateEmployee (@PathVariable Long id, @RequestBody DTO_RQ_EditEmployee dto) {
        if (id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (dto.getFullName() == null || dto.getFullName().isEmpty() ||
                dto.getRole() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: Dữ liệu vào không hợp lệ
        }
        if (!employeeService.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: User không tồn tại
        }
        try {
            DTO_RP_Employee updatedEmployee = employeeService.updateEmployee(id, dto);
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

    // UC_EM_06: Tìm kiếm nhân viên theo “tên”
    @GetMapping("/filter-by-name")
    public ResponseEntity<List<DTO_RP_Employee>> searchEmployeesByName(
            @RequestParam("fullName") String fullName,
            @RequestParam("companyId") Long companyId) {

        try {
            List<DTO_RP_Employee> employees = employeeService.searchEmployeesByName(fullName, companyId);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy nhân viên
            }
            return ResponseEntity.ok(employees); // 200: Trả về danh sách nhân viên
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    // UC_EM_07: Lọc nhân viên theo “vai trò”
    @GetMapping("/filter-by-role")
    public ResponseEntity<List<DTO_RP_Employee>> searchEmployeesByRole(
            @RequestParam("role") Integer role,
            @RequestParam("companyId") Long companyId) {

        try {
            List<DTO_RP_Employee> employees = employeeService.searchEmployeesByRole(role, companyId);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không tìm thấy nhân viên
            }
            return ResponseEntity.ok(employees); // 200: Trả về danh sách nhân viên
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }
    // UC_EM_08: Lấy danh sách nhân viên dựa vào ID của công ty
    @GetMapping("/list-employee/{companyId}")
    public ResponseEntity<List<DTO_RP_Employee>> getEmployeesByCompanyId(@PathVariable Long companyId) {
        if (companyId == null || companyId <= 0) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400: ID công ty không hợp lệ
        }
        try {
            List<DTO_RP_Employee> employees = employeeService.getEmployeesByCompanyId(companyId);
            if (employees.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404: Không có nhân viên nào thuộc công ty
            }
            return ResponseEntity.ok(employees); // 200: Trả về danh sách nhân viên
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Lỗi hệ thống
        }
    }

    @GetMapping("/list-driver/{companyId}")
    public ResponseEntity<List<DTO_RP_Driver>> getDriverByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_Driver> response = employeeService.getDriverByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }
    @GetMapping("/list-assistant/{companyId}")
    public ResponseEntity<List<DTO_RP_Assistant>> getAssistantByCompanyId(@PathVariable Long companyId) {
        try {
            List<DTO_RP_Assistant> response = employeeService.getAssistantByCompanyId(companyId);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500: Loi he thong
        }
    }















}
