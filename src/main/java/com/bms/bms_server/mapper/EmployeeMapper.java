package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Employee.request.CreateEmployeeDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Employee;

public class EmployeeMapper {
    public static Employee toEntity(CreateEmployeeDTO dto, Company company) {
        Employee employee = new Employee();
        employee.setCompany(company);
        employee.setFullName(dto.getFullName());
        employee.setUsername(dto.getUsername());
        employee.setPassword(dto.getPassword());
        employee.setRole(dto.getRole());
        employee.setPhone(dto.getPhone());
        employee.setStartDate(dto.getStartDate());
        employee.setBirthDate(dto.getBirthDate());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setAddress(dto.getAddress());
        employee.setStatus(dto.getStatus());
        return employee;
    }
}
