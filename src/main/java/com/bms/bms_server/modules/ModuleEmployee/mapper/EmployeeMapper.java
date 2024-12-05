package com.bms.bms_server.modules.ModuleEmployee.mapper;

import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RQ_CreateEmployee;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Assistant;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Driver;
import com.bms.bms_server.modules.ModuleEmployee.dto.DTO_RP_Employee;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.modules.ModuleEmployee.entity.Employee;

public class EmployeeMapper {
    public static Employee toEntity(DTO_RQ_CreateEmployee dto, Company company) {
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
    public static DTO_RP_Employee toDTO(Employee employee) {
        DTO_RP_Employee dto = new DTO_RP_Employee();
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
    public static DTO_RP_Driver toDriverResponseDTO(Employee employee) {
        DTO_RP_Driver dto = new DTO_RP_Driver();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        return dto;
    }
    public static DTO_RP_Assistant toAssistantResponseDTO(Employee employee) {
        DTO_RP_Assistant dto = new DTO_RP_Assistant();
        dto.setId(employee.getId());
        dto.setFullName(employee.getFullName());
        return dto;
    }
}
