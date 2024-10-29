package com.bms.bms_server.service;

import com.bms.bms_server.dto.Employee.request.EmployeeRequestDTO;
import com.bms.bms_server.dto.Employee.response.EmployeeResponseDTO;
import com.bms.bms_server.entity.Company;
import com.bms.bms_server.entity.Employee;
import com.bms.bms_server.repository.CompanyRepository;
import com.bms.bms_server.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    CompanyRepository companyRepository;
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }



    public EmployeeResponseDTO createEmployee(EmployeeRequestDTO dto) {
        Company company = companyRepository.findById(dto.getCompanyId()).orElseThrow(() -> new IllegalArgumentException("Company not found"));
        Employee newEmployee  = new Employee();
        newEmployee.setUsername(dto.getUsername());
        newEmployee.setPassword(encodePassword(dto.getPassword()));
        newEmployee.setStatus(dto.getStatus());
        newEmployee.setFullName(dto.getFullName());
        newEmployee.setPhoneNumber(dto.getPhoneNumber());
        newEmployee.setAddress(dto.getAddress());
        newEmployee.setEmail(dto.getEmail());
        newEmployee.setIdCard(dto.getIdCard());
        newEmployee.setGender(dto.getGender());
        newEmployee.setBirthDate(dto.getBirthDate());
        newEmployee.setRole(dto.getRole());
        newEmployee.setLicenseCategory(dto.getLicenseCategory());
        newEmployee.setExpirationDate(dto.getExpirationDate());
        newEmployee.setCompany(company);

        Employee savedEmployee = employeeRepository.save(newEmployee);
        return mapToResponseDTO(savedEmployee);
    }
    private EmployeeResponseDTO mapToResponseDTO(Employee savedEmployee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(savedEmployee.getId());
        dto.setUsername(savedEmployee.getUsername());
        dto.setStatus(savedEmployee.getStatus());
        dto.setFullName(savedEmployee.getFullName());
        dto.setPhoneNumber(savedEmployee.getPhoneNumber());
        dto.setAddress(savedEmployee.getAddress());
        dto.setEmail(savedEmployee.getEmail());
        dto.setIdCard(savedEmployee.getIdCard());
        dto.setGender(savedEmployee.getGender());
        dto.setBirthDate(savedEmployee.getBirthDate());
        dto.setRole(savedEmployee.getRole());
        dto.setLicenseCategory(savedEmployee.getLicenseCategory());
        dto.setExpirationDate(savedEmployee.getExpirationDate());
        return dto;
    }
    public boolean usernameExists(String username, Long companyId) {
        return employeeRepository.existsByUsernameAndCompanyId(username, companyId);
    }

    public boolean existsById(Long id) {
        return employeeRepository.existsById(id);
    }

    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
