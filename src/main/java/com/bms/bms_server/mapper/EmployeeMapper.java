package com.bms.bms_server.mapper;

import com.bms.bms_server.dto.Employee.request.CreateEmployeeDTO;
import com.bms.bms_server.dto.Employee.response.AssistantResponseDTO;
import com.bms.bms_server.dto.Employee.response.DriverResponseDTO;
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
        employee.setAccessBms(dto.getAccessBms());
        employee.setAccessCms(dto.getAccessCms());
        employee.setAccessTms(dto.getAccessTms());
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
        dto.setAccessBms(employee.getAccessBms());
        dto.setAccessCms(employee.getAccessCms());
        dto.setAccessTms(employee.getAccessTms());
        return dto;
    }
    public static DriverResponseDTO toDriverResponseDTO(Employee employee) {
        DriverResponseDTO dto = new DriverResponseDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        return dto;
    }
    public static AssistantResponseDTO toAssistantResponseDTO(Employee employee) {
        AssistantResponseDTO dto = new AssistantResponseDTO();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        return dto;
    }
}
