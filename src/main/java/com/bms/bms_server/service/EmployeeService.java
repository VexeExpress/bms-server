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

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }
        Employee existingEmployee = optionalEmployee.get();
        existingEmployee.setUsername(dto.getUsername());
        existingEmployee.setFullName(dto.getFullName());
        existingEmployee.setRole(dto.getRole());
        existingEmployee.setPhoneNumber(dto.getPhoneNumber());
        existingEmployee.setAddress(dto.getAddress());
        existingEmployee.setEmail(dto.getEmail());
        existingEmployee.setIdCard(dto.getIdCard());
        existingEmployee.setGender(dto.getGender());
        existingEmployee.setBirthDate(dto.getBirthDate());
        existingEmployee.setStatus(dto.getStatus());
        existingEmployee.setExpirationDate(dto.getExpirationDate());
        existingEmployee.setLicenseCategory(dto.getLicenseCategory());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return convertToResponseDTO(updatedEmployee);
    }

    private EmployeeResponseDTO convertToResponseDTO(Employee employee) {
        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setId(employee.getId());
        responseDTO.setUsername(employee.getUsername());
        responseDTO.setFullName(employee.getFullName());
        responseDTO.setPhoneNumber(employee.getPhoneNumber());
        responseDTO.setEmail(employee.getEmail());
        responseDTO.setRole(employee.getRole());
        responseDTO.setAddress(employee.getAddress());
        responseDTO.setStatus(employee.getStatus());
        responseDTO.setGender(employee.getGender());
        responseDTO.setIdCard(employee.getIdCard());
        responseDTO.setLicenseCategory(employee.getLicenseCategory());
        responseDTO.setExpirationDate(employee.getExpirationDate());
        responseDTO.setBirthDate(employee.getBirthDate());
        return responseDTO;
    }

    public void lockAccountEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }
        Employee employee = optionalEmployee.get();
        employee.setStatus(false);
        employeeRepository.save(employee);
    }

    public void changePassAccountEmployee(Long id) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            throw new IllegalArgumentException("Nhân viên không tồn tại.");
        }
        Employee employee = optionalEmployee.get();
        employee.setPassword(passwordEncoder.encode("12345678"));
        employeeRepository.save(employee);
    }

    public List<EmployeeResponseDTO> getEmployeesByCompanyId(Long companyId) {
        List<Employee> employees = employeeRepository.findByCompanyId(companyId);
        return employees.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private EmployeeResponseDTO  convertToDto(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setId(employee.getId());
        dto.setUsername(employee.getUsername());
        dto.setStatus(employee.getStatus());
        dto.setFullName(employee.getFullName());
        dto.setPhoneNumber(employee.getPhoneNumber());
        dto.setAddress(employee.getAddress());
        dto.setEmail(employee.getEmail());
        dto.setIdCard(employee.getIdCard());
        dto.setGender(employee.getGender());
        dto.setBirthDate(employee.getBirthDate());
        dto.setLicenseCategory(employee.getLicenseCategory());
        dto.setExpirationDate(employee.getExpirationDate());
        return dto;
    }
}
