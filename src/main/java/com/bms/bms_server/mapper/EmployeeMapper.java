package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Employee.request.CreateEmployeeDTO;
import com.bms.bms_server.dto.Employee.request.EditEmployeeDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeDTO;
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
    public static EmployeeDTO toDTO(Employee employee) {
        EmployeeDTO dto = new EmployeeDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        dto.setUsername(employee.getUsername());
        dto.setPhone(employee.getPhone());
        dto.setRole(employee.getRole());
        dto.setEmail(employee.getEmail());
        dto.setAddress(employee.getAddress());
        dto.setStartDate(employee.getStartDate());
        dto.setBirthDate(employee.getBirthDate());
        dto.setGender(employee.getGender());
        dto.setStatus(employee.getStatus());
        return dto;
    }
}
