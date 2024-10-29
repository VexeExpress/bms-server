package com.bms.bms_server.controller;

import com.bms.bms_server.dto.Employee.request.EmployeeRequestDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeResponseDTO;
import com.bms.bms_server.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employee")
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    // UC_EM_01: Thêm nhân viên mới
    @PostMapping("/create")
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@RequestBody EmployeeRequestDTO dto){
        System.out.println(dto);
        if (dto.getUsername() == null || dto.getUsername().isEmpty() ||
                dto.getFullName() == null || dto.getFullName().isEmpty() ||
                dto.getPassword() == null || dto.getPassword().isEmpty() ||
                dto.getRole() == null || dto.getCompanyId() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); // 400
        }
        if (employeeService.usernameExists(dto.getUsername(), dto.getCompanyId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build(); // 409
        }
        try {
            EmployeeResponseDTO createdEmployee = employeeService.createEmployee(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee); // 201
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build(); // 400
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
        }
    }
    // Data import UC_EM_01 {
    //    "username": "dangtuanthanh",
    //    "password": "12345678",
    //    "status": true,
    //    "fullName": "Đặng Tuấn Thành",
    //    "phoneNumber": "0397892603",
    //    "address": "123 Main Street, HCM City",
    //    "email": "john.doe@example.com",
    //    "idCard": "123456789",
    //    "gender": true,
    //    "birthDate": "1990-01-01",
    //    "role": 1,
    //    "licenseCategory": 2,
    //    "expirationDate": "2025-01-01",
    //    "companyId": 1
    //}


}
